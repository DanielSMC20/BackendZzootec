package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.repository.SaleRepository;
import com.zzootec.admin.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final WebClient webClient;
    private final SaleRepository saleRepository;

    @Override
    public Map<String, Object> getClientPreferences(Long clientId) {

        // 🔹 Simulación simple (luego puedes hacerlo real)
        List<Map<String, Object>> sales = List.of(
                Map.of("category", "Samsung", "quantity", 3),
                Map.of("category", "Accesorios", "quantity", 5)
        );

        Map<String, Object> payload = Map.of(
                "clientId", clientId,
                "sales", sales
        );

        return webClient.post()
                .uri("/ai/preferences")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    @Override
    public Object getPromotionSuggestions() {

        Map<String, Object> payload = Map.of(
                "products", List.of(
                        Map.of("name", "Samsung A55", "stock", 30, "sales", 1),
                        Map.of("name", "Cargador", "stock", 10, "sales", 15)
                )
        );

        return webClient.post()
                .uri("/ai/promotions")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}