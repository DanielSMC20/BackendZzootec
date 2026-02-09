package com.zzootec.admin.controller;

import com.zzootec.admin.dto.sale.SaleRequestDto;
import com.zzootec.admin.dto.sale.SaleResponseDto;
import com.zzootec.admin.dto.saleitem.SaleItemRequestDto;
import com.zzootec.admin.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@CrossOrigin("*")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public SaleResponseDto create(@RequestBody SaleRequestDto dto) {
        return saleService.create(dto);
    }

    @GetMapping
    public List<SaleResponseDto> list() {
        return saleService.findAll();
    }

    @PostMapping("/seed")
    public String seedSalesData() {
        Random random = new Random();
        String[] productNames = {"Laptop Dell", "Mouse Logitech", "Teclado Mecánico", "Monitor LG", "Webcam HD", "Headset Gamer", "Mouse Pad", "Adaptador USB-C"};
        Double[] prices = {1200.0, 35.0, 150.0, 450.0, 120.0, 200.0, 25.0, 45.0};
        
        // Generar ventas para los últimos 30 días
        for (int day = 1; day <= 30; day++) {
            int transactions = random.nextInt(3) + 1; // 1-3 transacciones por día
            for (int t = 0; t < transactions; t++) {
                SaleRequestDto saleDto = new SaleRequestDto();
                saleDto.setChannel("ONLINE");
                saleDto.setDate(LocalDateTime.now().minusDays(30 - day));
                saleDto.setClientId(1L);
                
                List<SaleItemRequestDto> items = new ArrayList<>();
                int itemCount = random.nextInt(3) + 1; // 1-3 items por transacción
                double total = 0;
                
                for (int i = 0; i < itemCount; i++) {
                    int productIdx = random.nextInt(productNames.length);
                    int qty = random.nextInt(3) + 1;
                    Double price = prices[productIdx];
                    
                    SaleItemRequestDto item = new SaleItemRequestDto();
                    item.setProductId((long) (productIdx + 1));
                    item.setProductName(productNames[productIdx]);
                    item.setQuantity(qty);
                    item.setPrice(price);
                    item.setSubtotal(price * qty);
                    
                    items.add(item);
                    total += price * qty;
                }
                
                saleDto.setItems(items);
                saleDto.setTotal(total);
                
                try {
                    saleService.create(saleDto);
                } catch (Exception e) {
                    // Ignorar errores de duplicados
                }
            }
        }
        
        return "Se generaron datos de prueba de ventas";
    }
}

