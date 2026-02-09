package com.zzootec.admin.repository;

import com.zzootec.admin.entity.SaleItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    
    /**
     * Obtiene los productos comprados por un cliente específico
     * Retorna: [product_id, product_name, cantidad_total]
     */
    @Query("SELECT p.id, p.name, SUM(si.quantity) " +
           "FROM SaleItem si " +
           "JOIN si.sale s " +
           "JOIN si.product p " +
           "WHERE s.client.id = :clientId " +
           "GROUP BY p.id, p.name " +
           "ORDER BY SUM(si.quantity) DESC")
    List<Object[]> findProductsByClientId(@Param("clientId") Long clientId);

        @Query("SELECT p.id, p.name, SUM(si.quantity), SUM(si.subtotal) " +
            "FROM SaleItem si " +
            "JOIN si.product p " +
            "GROUP BY p.id, p.name " +
            "ORDER BY SUM(si.quantity) DESC")
        List<Object[]> findTopProducts(Pageable pageable);

        @Query("SELECT DISTINCT p.id " +
            "FROM SaleItem si " +
            "JOIN si.sale s " +
            "JOIN si.product p " +
            "WHERE s.date >= :fromDate")
        List<Long> findProductIdsSoldSince(@Param("fromDate") LocalDateTime fromDate);
}

