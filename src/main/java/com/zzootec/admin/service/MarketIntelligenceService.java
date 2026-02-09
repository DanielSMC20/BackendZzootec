package com.zzootec.admin.service;

import java.util.List;
import java.util.Map;

public interface MarketIntelligenceService {

    Map<String, Object> getOverview();

    List<Map<String, Object>> getTopProducts(int limit);

    List<Map<String, Object>> getSlowProducts(int days, int limit);

    List<Map<String, Object>> getInactiveClients(int days, int limit);

    List<Map<String, Object>> getCategoryStock();
}
