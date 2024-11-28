package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.Client;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    /**
     * Найти всех килентов
     *
     * @return список клиентов типа {@link Client}
     */
    @Transactional
    @Query("SELECT c FROM Client c")
    @NonNull
    List<Client> findAll();

    /**
     * Найти клиента по телефону
     *
     * @param phone телефон
     * @return модель клиента типа {@link Client}
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
     * @param passportNumber паспорт
     * @return true/false в зависимости от того, существует ли такой клиент
     */
    @Transactional
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Client c WHERE c.passportNumber = :passportNumber")
    boolean existsClientByPassportNumber(@Param("passportNumber") String passportNumber);

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