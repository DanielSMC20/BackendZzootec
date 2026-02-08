package com.zzootec.admin.controller;

import com.zzootec.admin.dto.client.ClientRequestDto;
import com.zzootec.admin.dto.client.ClientResponseDto;
import com.zzootec.admin.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ClientResponseDto create(@RequestBody ClientRequestDto dto) {
        return clientService.create(dto);
    }

    @GetMapping
    public List<ClientResponseDto> list() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public ClientResponseDto get(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }
}
