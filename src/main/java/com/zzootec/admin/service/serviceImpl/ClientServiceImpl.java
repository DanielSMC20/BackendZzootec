package com.zzootec.admin.service.serviceImpl;

import com.zzootec.admin.dto.client.ClientRequestDto;
import com.zzootec.admin.dto.client.ClientResponseDto;
import com.zzootec.admin.entity.Client;
import com.zzootec.admin.repository.ClientRepository;
import com.zzootec.admin.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientResponseDto create(ClientRequestDto dto) {

        Client client = Client.builder()
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .telefono(dto.getTelefono())
                .correo(dto.getCorreo())
                .canalOrigen(dto.getCanalOrigen())
                .fechaRegistro(LocalDateTime.now())
                .build();

        clientRepository.save(client);

        return map(client);
    }

    @Override
    public List<ClientResponseDto> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public ClientResponseDto findById(Long id) {
        return clientRepository.findById(id)
                .map(this::map)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    private ClientResponseDto map(Client c) {
        return ClientResponseDto.builder()
                .id(c.getId())
                .nombres(c.getNombres())
                .apellidos(c.getApellidos())
                .telefono(c.getTelefono())
                .correo(c.getCorreo())
                .canalOrigen(c.getCanalOrigen())
                .fechaRegistro(LocalDateTime.parse(c.getFechaRegistro().toString()))
                .build();
    }
}
