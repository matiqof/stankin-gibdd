package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    /**
     * Найти клиента по телефону
     *
     * @param phone телефон
     * @return модель клиента
     */
    @Transactional
    @Query("SELECT c FROM Client c WHERE c.phone = :phone")
    Client findByPhone(@Param("phone") String phone);

    /**
     * Проверить существует ли клиент с таким телефоном
     *
     * @param phone телефон
     * @return true/false в зависимости от того, существует ли такой клиент
     */
    @Transactional
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Client c WHERE c.phone = :phone")
    boolean existsClientByPhone(@Param("phone") String phone);

    /**
     * Проверить существует ли клиент с таким паспортом
     *
     * @param passport паспорт
     * @return true/false в зависимости от того, существует ли такой клиент
     */
    @Transactional
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Client c WHERE c.passport = :passport")
    boolean existsClientByPassport(@Param("passport") String passport);

    /**
     * Удалить клиента по телефону
     *
     * @param phone телефон
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Client c WHERE c.phone = :phone")
    void deleteByPhone(@Param("phone") String phone);
}