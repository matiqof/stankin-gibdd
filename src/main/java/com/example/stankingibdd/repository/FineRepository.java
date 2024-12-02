package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.Fine;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface FineRepository extends JpaRepository<Fine, UUID> {

    /**
     * Найти все штрафы
     *
     * @return список штрафов типа {@link Fine}
     */
    @Transactional
    @Query("SELECT f FROM Fine f")
    @NonNull
    List<Fine> findAll();

    /**
     * Найти штраф по идентификатору
     *
     * @param fineId идентификатор штрафа
     * @return модель штрафа типа {@link Fine}
     */
    @Transactional
    @Query("SELECT f FROM Fine f WHERE f.fineId = :fineId")
    Fine findByFineId(@Param("fineId") UUID fineId);

    /**
     * Проверить существует ли штраф с таким идентификатором
     *
     * @param fineId идентификатор штрафа
     * @return true/false в зависимости от того, существует ли такой штраф
     */
    @Transactional
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Fine f WHERE f.fineId = :fineId")
    boolean existsFineByFineId(@Param("fineId") UUID fineId);

    /**
     * Удалить штраф по идентификатору
     *
     * @param fineId идентификатор штрафа
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Fine f WHERE f.fineId = :fineId")
    void deleteByFineId(@Param("fineId") UUID fineId);
}