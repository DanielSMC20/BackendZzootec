package com.zzootec.admin.controller;

import com.zzootec.admin.service.MarketIntelligenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/market")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class MarketIntelligenceController {

    private final MarketIntelligenceService marketIntelligenceService;

    @GetMapping("/overview")
    public Map<String, Object> getOverview() {
        log.info("GET /api/market/overview");
        return marketIntelligenceService.getOverview();
    }

    @GetMapping("/top-products")
    public List<Map<String, Object>> getTopProducts(@RequestParam(defaultValue = "5") int limit) {
        log.info("GET /api/market/top-products?limit={}", limit);
        return marketIntelligenceService.getTopProducts(limit);
    }

    @GetMapping("/slow-products")
    public List<Map<String, Object>> getSlowProducts(
            @RequestParam(defaultValue = "30") int days,
            @RequestParam(defaultValue = "10") int limit) {
        log.info("GET /api/market/slow-products?days={}&limit={}", days, limit);
        return marketIntelligenceService.getSlowProducts(days, limit);
    }

    @GetMapping("/inactive-clients")
    public List<Map<String, Object>> getInactiveClients(
            @RequestParam(defaultValue = "60") int days,
            @RequestParam(defaultValue = "10") int limit) {
        log.info("GET /api/market/inactive-clients?days={}&limit={}", days, limit);
        return marketIntelligenceService.getInactiveClients(days, limit);
    }

    @GetMapping("/category-stock")
    public List<Map<String, Object>> getCategoryStock() {
        log.info("GET /api/market/category-stock");
        return marketIntelligenceService.getCategoryStock();
    }
}
