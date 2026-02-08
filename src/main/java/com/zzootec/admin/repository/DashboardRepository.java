package com.zzootec.admin.repository;

import com.zzootec.admin.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Sale, Long> {

    @Query("""
        SELECT SUM(s.total),
               COUNT(s),
               AVG(s.total)
        FROM Sale s
    """)
    Object[] summary();

    @Query("""
        SELECT MONTH(s.date), SUM(s.total)
        FROM Sale s
        GROUP BY MONTH(s.date)
        ORDER BY MONTH(s.date)
    """)
    List<Object[]> salesByMonth();

    @Query("""
        SELECT p.name, SUM(i.quantity)
        FROM SaleItem i
        JOIN i.product p
        GROUP BY p.name
        ORDER BY SUM(i.quantity) DESC
    """)
    List<Object[]> topProducts();
}
