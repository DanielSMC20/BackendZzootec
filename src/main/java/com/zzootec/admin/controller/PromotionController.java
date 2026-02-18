package com.zzootec.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PromotionController {

    // Almacenamiento temporal de sugerencias (en memoria)
    // En producción deberías guardar esto en base de datos
    private final List<Map<String, Object>> suggestions = new ArrayList<>();

    // =======================
    // ENDPOINT PARA N8N: GUARDAR SUGERENCIAS DE IA
    // =======================
    @PostMapping("/save-suggestions")
    public Map<String, Object> saveSuggestions(@RequestBody Map<String, Object> payload) {
        
        // Agregar timestamp
        payload.put("savedAt", LocalDateTime.now().toString());
        payload.put("status", "pending");
        
        // Guardar en memoria (temporal)
        suggestions.add(payload);
        
        return Map.of(
            "success", true,
            "message", "Sugerencias guardadas correctamente",
            "totalSuggestions", suggestions.size(),
            "data", payload
        );
    }

    // =======================
    // LISTAR TODAS LAS SUGERENCIAS
    // =======================
    @GetMapping
    public Map<String, Object> getAllSuggestions() {
        return Map.of(
            "total", suggestions.size(),
            "suggestions", suggestions
        );
    }

    // =======================
    // OBTENER ÚLTIMA SUGERENCIA
    // =======================
    @GetMapping("/latest")
    public Map<String, Object> getLatestSuggestion() {
        if (suggestions.isEmpty()) {
            return Map.of(
                "success", false,
                "message", "No hay sugerencias disponibles"
            );
        }
        
        return Map.of(
            "success", true,
            "suggestion", suggestions.get(suggestions.size() - 1)
        );
    }

    // =======================
    // LIMPIAR SUGERENCIAS (útil para testing)
    // =======================
    @DeleteMapping
    public Map<String, Object> clearSuggestions() {
        int count = suggestions.size();
        suggestions.clear();
        
        return Map.of(
            "success", true,
            "message", "Sugerencias eliminadas",
            "deletedCount", count
        );
    }
}
