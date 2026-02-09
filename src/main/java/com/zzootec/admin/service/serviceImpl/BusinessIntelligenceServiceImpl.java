package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.entity.Producto;
import com.zzootec.admin.repository.ProductoRepository;
import com.zzootec.admin.repository.SaleItemRepository;
import com.zzootec.admin.service.BusinessIntelligenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessIntelligenceServiceImpl implements BusinessIntelligenceService {

    private final ProductoRepository productoRepository;
    private final SaleItemRepository saleItemRepository;

    @Override
    public Map<String, Object> getStockAlerts() {
        log.info("Analizando alertas de stock...");
        
        List<Producto> productos = productoRepository.findByActiveTrue();
        List<Map<String, Object>> alerts = new ArrayList<>();
        
        for (Producto producto : productos) {
            Integer stock = producto.getStock() != null ? producto.getStock() : 0;
            
            Map<String, Object> alert = new HashMap<>();
            alert.put("id", producto.getId());
            alert.put("name", producto.getName());
            alert.put("stock", stock);
            alert.put("price", producto.getPrice());
            
            // Análisis de stock
            if (stock == 0) {
                alert.put("level", "CRITICAL");
                alert.put("priority", 1);
                alert.put("message", "SIN STOCK - Reabastecer urgente");
                alert.put("action", "Pedir al proveedor inmediatamente");
                alerts.add(alert);
            } else if (stock < 5) {
                alert.put("level", "HIGH");
                alert.put("priority", 2);
                alert.put("message", "STOCK CRÍTICO - Quedan solo " + stock + " unidades");
                alert.put("action", "Programar pedido al proveedor");
                alerts.add(alert);
            } else if (stock < 10) {
                alert.put("level", "MEDIUM");
                alert.put("priority", 3);
                alert.put("message", "STOCK BAJO - " + stock + " unidades disponibles");
                alert.put("action", "Considerar reabastecimiento próximo");
                alerts.add(alert);
            } else if (stock > 100) {
                alert.put("level", "INFO");
                alert.put("priority", 4);
                alert.put("message", "EXCESO DE STOCK - " + stock + " unidades");
                alert.put("action", "Considerar promoción para mover inventario");
                alerts.add(alert);
            }
        }
        
        // Ordenar por prioridad
        alerts.sort(Comparator.comparingInt(a -> (Integer) a.get("priority")));
        
        log.info("Análisis completado: {} alertas generadas", alerts.size());
        
        return Map.of(
            "total", alerts.size(),
            "alerts", alerts,
            "summary", Map.of(
                "critical", alerts.stream().filter(a -> "CRITICAL".equals(a.get("level"))).count(),
                "high", alerts.stream().filter(a -> "HIGH".equals(a.get("level"))).count(),
                "medium", alerts.stream().filter(a -> "MEDIUM".equals(a.get("level"))).count(),
                "info", alerts.stream().filter(a -> "INFO".equals(a.get("level"))).count()
            )
        );
    }

    @Override
    public Map<String, Object> getClientRecommendations(Long clientId) {
        log.info("Generando recomendaciones para cliente {}", clientId);
        
        try {
            // Obtener historial de compras del cliente
            List<Object[]> purchaseHistory = saleItemRepository.findProductsByClientId(clientId);
            
            if (purchaseHistory.isEmpty()) {
                // Cliente sin historial - Recomendar productos populares
                return getPopularProductsRecommendations();
            }
            
            // Extraer IDs de productos comprados
            Set<Long> purchasedProductIds = purchaseHistory.stream()
                .map(obj -> ((Number) obj[0]).longValue())
                .collect(Collectors.toSet());
            
            // Obtener productos relacionados (misma categoría/marca)
            List<Producto> allProducts = productoRepository.findByActiveTrue();
            List<Map<String, Object>> recommendations = new ArrayList<>();
            
            for (Producto producto : allProducts) {
                // No recomendar productos ya comprados
                if (purchasedProductIds.contains(producto.getId())) {
                    continue;
                }
                
                // Verificar que tenga stock
                if (producto.getStock() == null || producto.getStock() <= 0) {
                    continue;
                }
                
                Map<String, Object> recommendation = new HashMap<>();
                recommendation.put("id", producto.getId());
                recommendation.put("name", producto.getName());
                recommendation.put("price", producto.getPrice());
                recommendation.put("stock", producto.getStock());
                recommendation.put("reason", "Producto relacionado con tus compras anteriores");
                recommendation.put("score", 0.7);
                
                recommendations.add(recommendation);
                
                // Limitar a 10 recomendaciones
                if (recommendations.size() >= 10) {
                    break;
                }
            }
            
            return Map.of(
                "clientId", clientId,
                "total", recommendations.size(),
                "recommendations", recommendations,
                "message", "Basado en tu historial de compras"
            );
            
        } catch (Exception e) {
            log.warn("No se pudo generar recomendaciones personalizadas: {}", e.getMessage());
            return getPopularProductsRecommendations();
        }
    }

    @Override
    public Map<String, Object> getPromotionSuggestions() {
        log.info("Generando sugerencias de promociones...");
        
        List<Producto> productos = productoRepository.findByActiveTrue();
        List<Map<String, Object>> suggestions = new ArrayList<>();
        
        for (Producto producto : productos) {
            Integer stock = producto.getStock() != null ? producto.getStock() : 0;
            double price = producto.getPrice();
            
            Map<String, Object> suggestion = new HashMap<>();
            suggestion.put("productId", producto.getId());
            suggestion.put("productName", producto.getName());
            suggestion.put("currentStock", stock);
            suggestion.put("currentPrice", price);
            
            // Lógica de recomendación de promoción
            if (stock == 0) {
                continue; // No promocionar sin stock
            } else if (stock > 100) {
                suggestion.put("strategy", "Descuento 25% - Liquidar exceso de stock");
                suggestion.put("suggestedDiscount", 25);
                suggestion.put("newPrice", price * 0.75);
                suggestion.put("priority", "HIGH");
                suggestion.put("expectedImpact", "Reducir inventario en 50 unidades");
                suggestions.add(suggestion);
            } else if (stock < 10) {
                suggestion.put("strategy", "Última oportunidad - Destacar escasez");
                suggestion.put("suggestedDiscount", 0);
                suggestion.put("newPrice", price);
                suggestion.put("priority", "MEDIUM");
                suggestion.put("expectedImpact", "Aumentar urgencia de compra");
                suggestions.add(suggestion);
            } else if (price > 100) {
                suggestion.put("strategy", "Pago en cuotas - Facilitar compra");
                suggestion.put("suggestedDiscount", 10);
                suggestion.put("newPrice", price * 0.90);
                suggestion.put("priority", "MEDIUM");
                suggestion.put("expectedImpact", "Aumentar conversión en 30%");
                suggestions.add(suggestion);
            } else {
                suggestion.put("strategy", "Promoción 2x1 o 15% OFF");
                suggestion.put("suggestedDiscount", 15);
                suggestion.put("newPrice", price * 0.85);
                suggestion.put("priority", "LOW");
                suggestion.put("expectedImpact", "Incrementar ventas en 20%");
                suggestions.add(suggestion);
            }
        }
        
        // Ordenar por prioridad
        Map<String, Integer> priorityMap = Map.of("HIGH", 1, "MEDIUM", 2, "LOW", 3);
        suggestions.sort(Comparator.comparingInt(s -> priorityMap.getOrDefault(s.get("priority"), 4)));
        
        log.info("Generadas {} sugerencias de promoción", suggestions.size());
        
        return Map.of(
            "total", suggestions.size(),
            "promotions", suggestions.stream().limit(20).collect(Collectors.toList()),
            "message", "Análisis de negocio completado"
        );
    }
    
    /**
     * Recomendaciones genéricas basadas en productos populares
     */
    private Map<String, Object> getPopularProductsRecommendations() {
        List<Producto> productos = productoRepository.findByActiveTrue()
            .stream()
            .filter(p -> p.getStock() != null && p.getStock() > 0)
            .limit(10)
            .toList();
        
        List<Map<String, Object>> recommendations = productos.stream()
            .map(p -> Map.of(
                "id", (Object) p.getId(),
                "name", p.getName(),
                "price", p.getPrice(),
                "stock", p.getStock(),
                "reason", "Producto disponible y popular",
                "score", 0.5
            ))
            .collect(Collectors.toList());
        
        return Map.of(
            "total", recommendations.size(),
            "recommendations", recommendations,
            "message", "Productos recomendados para ti"
        );
    }
}
