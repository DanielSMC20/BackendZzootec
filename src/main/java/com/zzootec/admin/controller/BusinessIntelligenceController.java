package com.zzootec.admin.controller;

import com.zzootec.admin.service.BusinessIntelligenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/intelligence")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class BusinessIntelligenceController {

    private final BusinessIntelligenceService businessIntelligenceService;

    /**
     * Obtiene alertas de productos con stock bajo o crítico
     * GET /api/intelligence/stock-alerts
     */
    @GetMapping("/stock-alerts")
    public Map<String, Object> getStockAlerts() {
        log.info("GET /api/intelligence/stock-alerts");
        return businessIntelligenceService.getStockAlerts();
    }

    /**
     * Obtiene recomendaciones personalizadas para un cliente
     * GET /api/intelligence/recommendations/{clientId}
     */
    @GetMapping("/recommendations/{clientId}")
    public Map<String, Object> getClientRecommendations(@PathVariable Long clientId) {
        log.info("GET /api/intelligence/recommendations/{}", clientId);
        return businessIntelligenceService.getClientRecommendations(clientId);
    }

    /**
     * Obtiene sugerencias de promociones basadas en análisis de productos
     * GET /api/intelligence/promotions
     */
    @GetMapping("/promotions")
    public Map<String, Object> getPromotionSuggestions() {
        log.info("GET /api/intelligence/promotions");
        return businessIntelligenceService.getPromotionSuggestions();
    }
}
