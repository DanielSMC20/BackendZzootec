package com.zzootec.admin.repository;

import com.zzootec.admin.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findByActiveTrue();
}
