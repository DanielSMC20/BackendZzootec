package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.repository.SaleRepository;
import com.zzootec.admin.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiServiceImpl implements AiService {

    private final WebClient webClient;
    private final SaleRepository saleRepository;

    @Override
    public Map<String, Object> getClientPreferences(Long clientId) {
        try {
            List<Map<String, Object>> sales = List.of(
                    Map.of("category", "Samsung", "quantity", 3),
                    Map.of("category", "Accesorios", "quantity", 5)
            );

            Map<String, Object> payload = Map.of(
                    "clientId", clientId,
                    "sales", sales
            );

            Map<String, Object> result = webClient.post()
                    .uri("/ai/preferences")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(5))
                    .onErrorReturn(Map.of(
                            "message", "Servicio de IA no disponible",
                            "preferences", List.of("Samsung", "Accesorios")
                    ))
                    .block();
            
            return result;
        } catch (Exception e) {
            log.error("Error al consultar IA: {}", e.getMessage(), e);
            return Map.of(
                    "message", "Servicio de IA no disponible",
                    "preferences", List.of("Samsung", "Accesorios")
            );
        }
    }

    @Override
    public Object getPromotionSuggestions() {
        log.info("Solicitando sugerencias de promociones...");
        
        // Datos mock por defecto
        Map<String, Object> mockData = Map.of(
                "message", "Servicio de IA no disponible",
                "promotions", List.of(
                        Map.of("product", "Samsung A55", "strategy", "Promoción 2x1"),
                        Map.of("product", "Cargador", "strategy", "Descuento 20%")
                )
        );
        
        try {
            Map<String, Object> payload = Map.of(
                    "products", List.of(
                            Map.of("name", "Samsung A55", "stock", 30, "sales", 1),
                            Map.of("name", "Cargador", "stock", 10, "sales", 15)
                    )
            );

            log.info("Llamando a IA en /ai/promotions...");
            
            Object result = webClient.post()
                    .uri("/ai/promotions")
                    .bodyValue(payload)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .timeout(Duration.ofSeconds(10))
                    .doOnError(error -> log.error("Error en llamada IA: {}", error.getMessage()))
                    .onErrorReturn(mockData)
                    .block(Duration.ofSeconds(12));
            
            log.info("Respuesta recibida de IA");
            return result != null ? result : mockData;
            
        } catch (Exception e) {
            log.error("Error al consultar IA (catch): {}", e.getMessage(), e);
            return mockData;
        }
    }
}