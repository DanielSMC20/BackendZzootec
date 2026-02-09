package com.zzootec.admin.repository;

import com.zzootec.admin.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	long countByDateAfter(LocalDateTime fromDate);

	@Query("SELECT COALESCE(SUM(s.total), 0) FROM Sale s WHERE s.date >= :fromDate")
	Double sumTotalSince(@Param("fromDate") LocalDateTime fromDate);

	@Query("SELECT s.client.id, MAX(s.date) FROM Sale s GROUP BY s.client.id")
	List<Object[]> findLastSalePerClient();

}
