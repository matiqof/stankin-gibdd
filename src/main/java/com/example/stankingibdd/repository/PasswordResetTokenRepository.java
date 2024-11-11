package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    /**
     * Получить запись по токену
     *
     * @param token токен
     * @return запись
     */
    PasswordResetToken findByToken(String token);

    /**
     * Поиск записей по телефону
     *
     * @param phone телефон клиента
     * @return список записей
     */
    @Query("SELECT prt FROM PasswordResetToken prt WHERE prt.client.phone = :phone")
    List<PasswordResetToken> findByPhone(@Param("phone") String phone);
}