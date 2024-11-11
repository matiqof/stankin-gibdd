package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    /**
     * Найти клиента по телефону
     *
     * @param phone телефон
     * @return модель клиента
     */
    Client findByPhone(String phone);

    /**
     * Проверить существует ли клиент с таким телефоном
     *
     * @param phone телефон
     * @return true/false в зависимости от того, существует ли такой клиент
     */
    boolean existsClientByPhone(String phone);

    /**
     * Проверить существует ли клиент с таким паспортом
     *
     * @param passport паспорт
     * @return true/false в зависимости от того, существует ли такой клиент
     */
    boolean existsClientByPassport(String passport);
}
