package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.entity.Client;
import com.zzootec.admin.entity.Producto;
import com.zzootec.admin.repository.ClientRepository;
import com.zzootec.admin.repository.ProductoRepository;
import com.zzootec.admin.repository.SaleItemRepository;
import com.zzootec.admin.repository.SaleRepository;
import com.zzootec.admin.service.MarketIntelligenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarketIntelligenceServiceImpl implements MarketIntelligenceService {

    private final ClientRepository clientRepository;
    private final ProductoRepository productoRepository;
    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;

    @Override
    public Map<String, Object> getOverview() {
        LocalDateTime since30 = LocalDateTime.now().minusDays(30);
        long totalClients = clientRepository.count();
        long totalProducts = productoRepository.count();
        long totalSales = saleRepository.count();
        long salesLast30 = saleRepository.countByDateAfter(since30);
        double revenueLast30 = Optional.ofNullable(saleRepository.sumTotalSince(since30)).orElse(0.0);

        return Map.of(
                "totalClients", totalClients,
                "totalProducts", totalProducts,
                "totalSales", totalSales,
                "salesLast30Days", salesLast30,
                "revenueLast30Days", revenueLast30
        );
    }

    @Override
    public List<Map<String, Object>> getTopProducts(int limit) {
        List<Object[]> rows = saleItemRepository.findTopProducts(PageRequest.of(0, limit));
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {
            Map<String, Object> item = new HashMap<>();
            item.put("productId", ((Number) row[0]).longValue());
            item.put("productName", row[1]);
            item.put("totalQuantity", ((Number) row[2]).longValue());
            item.put("totalRevenue", row[3] != null ? ((Number) row[3]).doubleValue() : 0.0);
            result.add(item);
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> getSlowProducts(int days, int limit) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        List<Long> soldIds = saleItemRepository.findProductIdsSoldSince(since);
        Set<Long> soldSet = new HashSet<>(soldIds);

        List<Producto> productos = productoRepository.findByActiveTrue();
        List<Map<String, Object>> slow = new ArrayList<>();

        for (Producto p : productos) {
            if (p.getId() == null || soldSet.contains(p.getId())) {
                continue;
            }
            Integer stock = p.getStock() != null ? p.getStock() : 0;
            Map<String, Object> item = new HashMap<>();
            item.put("productId", p.getId());
            item.put("productName", p.getName());
            item.put("stock", stock);
            item.put("price", p.getPrice());
            item.put("daysWithoutSales", days);
            slow.add(item);

            if (slow.size() >= limit) {
                break;
            }
        }

        return slow;
    }

    @Override
    public List<Map<String, Object>> getInactiveClients(int days, int limit) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        Map<Long, LocalDateTime> lastSaleByClient = new HashMap<>();

        for (Object[] row : saleRepository.findLastSalePerClient()) {
            Long clientId = ((Number) row[0]).longValue();
            LocalDateTime lastSale = (LocalDateTime) row[1];
            lastSaleByClient.put(clientId, lastSale);
        }

        List<Client> clients = clientRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Client client : clients) {
            LocalDateTime lastSale = lastSaleByClient.get(client.getId());
            boolean inactive = lastSale == null || lastSale.isBefore(since);

            if (!inactive) {
                continue;
            }

            long daysInactive = lastSale == null
                    ? days
                    : ChronoUnit.DAYS.between(lastSale, LocalDateTime.now());

            Map<String, Object> item = new HashMap<>();
            item.put("clientId", client.getId());
            item.put("fullName", (client.getNombres() + " " + client.getApellidos()).trim());
            item.put("correo", client.getCorreo());
            item.put("telefono", client.getTelefono());
            item.put("lastSale", lastSale);
            item.put("daysInactive", daysInactive);
            result.add(item);

            if (result.size() >= limit) {
                break;
            }
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> getCategoryStock() {
        List<Object[]> rows = productoRepository.sumStockByCategory();
        return rows.stream()
                .map(r -> Map.of(
                        "category", r[0],
                        "totalStock", r[1] != null ? ((Number) r[1]).longValue() : 0
                ))
                .collect(Collectors.toList());
    }
}
