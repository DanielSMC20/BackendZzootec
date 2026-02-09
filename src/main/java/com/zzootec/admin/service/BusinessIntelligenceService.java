package com.zzootec.admin.service;

import java.util.List;
import java.util.Map;

public interface BusinessIntelligenceService {
    
    /**
     * Obtiene alertas de productos con stock bajo/crítico
     */
    Map<String, Object> getStockAlerts();
    
    /**
     * Obtiene recomendaciones de productos para un cliente específico
     * basado en su historial de compras
     */
    Map<String, Object> getClientRecommendations(Long clientId);
    
    /**
     * Sugiere promociones basadas en análisis de productos
     */
    Map<String, Object> getPromotionSuggestions();
}
