package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.dto.sale.SaleRequestDto;
import com.zzootec.admin.dto.sale.SaleResponseDto;
import com.zzootec.admin.dto.saleitem.SaleItemRequestDto;
import com.zzootec.admin.entity.Client;
import com.zzootec.admin.entity.Producto;
import com.zzootec.admin.entity.Sale;
import com.zzootec.admin.entity.SaleItem;
import com.zzootec.admin.mapper.SaleMapper;
import com.zzootec.admin.repository.ClientRepository;
import com.zzootec.admin.repository.ProductoRepository;
import com.zzootec.admin.repository.SaleRepository;
import com.zzootec.admin.service.InventoryMovementService;
import com.zzootec.admin.service.SaleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final ProductoRepository productRepository;
    private final ClientRepository clientRepository;
    private final InventoryMovementService inventoryService;
    private final SaleMapper saleMapper; // ✅ IMPORTANTE

    @Override
    @Transactional
    public SaleResponseDto create(SaleRequestDto dto) {

        // ================= VALIDAR CLIENTE =================
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() ->
                        new RuntimeException("Cliente no encontrado"));

        // ================= CREAR CABECERA =================
        Sale sale = Sale.builder()
                .client(client)
                .date(LocalDateTime.now())
                .channel(dto.getChannel())
                .total(0.0)
                .build();

        saleRepository.save(sale);

        double total = 0;
        List<SaleItem> items = new ArrayList<>();

        // ================= DETALLE =================
        for (SaleItemRequestDto itemDto : dto.getItems()) {

            Producto product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Producto no encontrado"));

            // 🔒 VALIDAR STOCK
            if (product.getStock() < itemDto.getQuantity()) {
                throw new RuntimeException(
                        "Stock insuficiente para el producto: " + product.getName()
                );
            }

            double subtotal = product.getPrice() * itemDto.getQuantity();

            SaleItem item = SaleItem.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .price(product.getPrice())
                    .subtotal(subtotal)
                    .build();

            // ================= KARDEX =================
            inventoryService.registerExit(
                    product,
                    itemDto.getQuantity(),
                    "VENTA"
            );

            // ================= ACTUALIZAR STOCK =================
            product.setStock(product.getStock() - itemDto.getQuantity());
            productRepository.save(product);

            items.add(item);
            total += subtotal;
        }

        // ================= FINAL =================
        sale.setItems(items);
        sale.setTotal(total);

        Sale savedSale = saleRepository.save(sale);

        // ✅ DEVOLVER DTO LIMPIO
        return saleMapper.toDto(savedSale);
    }

    // ================= LISTAR VENTAS =================
    @Override
    public List<SaleResponseDto> findAll() {
        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toDto)
                .toList();
    }


}
