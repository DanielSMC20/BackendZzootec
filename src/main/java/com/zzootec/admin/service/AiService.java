package com.zzootec.admin.service;

import java.util.Map;

public interface AiService {

    Map<String, Object> getClientPreferences(Long clientId);

    Object getPromotionSuggestions();
}