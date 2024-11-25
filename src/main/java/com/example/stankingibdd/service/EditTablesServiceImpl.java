package com.example.stankingibdd.service;

import com.example.stankingibdd.entity.Client;
import com.example.stankingibdd.exception.EditTablesException;
import com.example.stankingibdd.mapper.ClientMapper;
import com.example.stankingibdd.model.ClientDto;
import com.example.stankingibdd.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EditTablesServiceImpl implements EditTablesService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Override
    public void addClient(ClientDto clientDto) {
        if (Objects.isNull(clientDto)) {
            final String errorMessage = "Невозможно добавить пустого клиента";
            throw new EditTablesException(errorMessage);
        }

        String phone = clientDto.getPhone();
        if (clientRepository.existsClientByPhone(phone)) {
            final String errorMessage = "Клиент с телефоном " + phone + ", который уже существует";
            throw new EditTablesException(errorMessage);
        }

        String passport = clientDto.getPassport();
        if (clientRepository.existsClientByPassport(passport)) {
            final String errorMessage = "Клиент с паспортом " + passport + ", который уже существует";
            throw new EditTablesException(errorMessage);
        }

        Client client = clientMapper.map(clientDto);
        client.setClientPassword(passwordEncoder.encode(clientDto.getClientPassword()));

        clientRepository.save(client);
    }

    @Override
    public void editClient(ClientDto clientDto) {
        clientService.saveClient(clientDto);
    }

    @Override
    public void deleteClient(String phone) {
        if (!StringUtils.hasLength(phone)) {
            final String errorMessage = "Невозможно удалить клиента с пустым телефоном";
            throw new EditTablesException(errorMessage);
        }

        if (!clientRepository.existsClientByPhone(phone)) {
            final String errorMessage = "Клиент с телефоном " + phone + ", который не существует";
            throw new EditTablesException(errorMessage);
        }

        clientRepository.deleteByPhone(phone);
    }
}
