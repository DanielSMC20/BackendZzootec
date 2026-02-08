package com.zzootec.admin.controller;

import com.zzootec.admin.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AiController {

    private final AiService aiService;

    @GetMapping("/preferences/{clientId}")
    public Object preferences(@PathVariable Long clientId) {
        return aiService.getClientPreferences(clientId);
    }

    @GetMapping("/promotions")
    public Object promotions() {
        return aiService.getPromotionSuggestions();
    }
}
