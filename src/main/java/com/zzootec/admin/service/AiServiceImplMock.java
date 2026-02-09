package com.zzootec.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
public class AiServiceImplMock implements AiService {

    @Override
    public Map<String, Object> getClientPreferences(Long clientId) {
        log.info("MOCK: Devolviendo preferencias mock para cliente {}", clientId);
        return Map.of(
                "clientId", clientId,
                "preferences", List.of(
                        Map.of("category", "Smartphones", "interest", 0.85),
                        Map.of("category", "Accesorios", "interest", 0.65)
                ),
                "message", "Datos de prueba (mock)"
        );
    }

    @Override
    public Object getPromotionSuggestions() {
        log.info("MOCK: Devolviendo promociones mock");
        return Map.of(
                "message", "Datos de prueba (mock)",
                "promotions", List.of(
                        Map.of(
                                "product", "Samsung A55",
                                "strategy", "Promoción 2x1 - Alta demanda",
                                "score", 0.92
                        ),
                        Map.of(
                                "product", "Cargador USB-C",
                                "strategy", "Descuento 20% - Movimiento lento",
                                "score", 0.78
                        ),
                        Map.of(
                                "product", "Auriculares Bluetooth",
                                "strategy", "Bundle con smartphone",
                                "score", 0.65
                        )
                )
        );
    }
}
