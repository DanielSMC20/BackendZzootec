package com.zzootec.admin.repository;

import com.zzootec.admin.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
