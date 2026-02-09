package com.zzootec.admin.controller;

import com.zzootec.admin.service.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
public class AiController {

    private final AiService aiService;

    @GetMapping("/preferences/{clientId}")
    public Object preferences(@PathVariable Long clientId) {
        log.info("GET /api/ai/preferences/{}", clientId);
        return aiService.getClientPreferences(clientId);
    }

    @GetMapping("/promotions")
    public Object promotions() {
        log.info("GET /api/ai/promotions - iniciando...");
        try {
            Object result = aiService.getPromotionSuggestions();
            log.info("GET /api/ai/promotions - respuesta obtenida");
            return result;
        } catch (Exception e) {
            log.error("Error en /api/ai/promotions: {}", e.getMessage(), e);
            return Map.of(
                    "error", "Error interno",
                    "message", e.getMessage()
            );
        }
    }
}
