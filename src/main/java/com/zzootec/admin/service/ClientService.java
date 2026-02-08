package com.zzootec.admin.service;

import com.zzootec.admin.dto.client.ClientRequestDto;
import com.zzootec.admin.dto.client.ClientResponseDto;

import java.util.List;

public interface ClientService {

    ClientResponseDto create(ClientRequestDto dto);

    List<ClientResponseDto> findAll();

    ClientResponseDto findById(Long id);

    void delete(Long id);
}
