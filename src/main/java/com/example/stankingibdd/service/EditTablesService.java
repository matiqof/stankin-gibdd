package com.example.stankingibdd.service;

import com.example.stankingibdd.model.ClientDto;

public interface EditTablesService {

    /**
     * Добавить нового клиента
     *
     * @param clientDto модель клиента {@link ClientDto}
     */
    void addClient(ClientDto clientDto);

    /**
     * Изменить клиента
     *
     * @param clientDto модель клиента {@link ClientDto}
     */
    void editClient(ClientDto clientDto);

    /**
     * Удалить клиента
     *
     * @param phone телефон клиента
     */
    void deleteClient(String phone);
}
