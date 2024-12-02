package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.Accident;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface AccidentRepository extends JpaRepository<Accident, UUID> {

    /**
     * Найти все аварии
     *
     * @return список аварий типа {@link Accident}
     */
    @Transactional
    @Query("SELECT a FROM Accident a")
    @NonNull
    List<Accident> findAll();

    /**
     * Найти аварию по идентификатору
     *
     * @param accidentId идентификатор аварии
     * @return модель аварии типа {@link Accident}
     */
    @Transactional
    @Query("SELECT a FROM Accident a WHERE a.accidentId = :accidentId")
    Accident findByAccidentId(@Param("accidentId") UUID accidentId);

    /**
     * Проверить существует ли авария с таким идентификатором
     *
     * @param accidentId идентификатор аварии
     * @return true/false в зависимости от того, существует ли такая авария
     */
    @Transactional
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Accident a WHERE a.accidentId = :accidentId")
    boolean existsAccidentByAccidentId(@Param("accidentId") UUID accidentId);

    /**
     * Удалить аварию по идентификатору
     *
     * @param accidentId идентификатор аварии
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Accident a WHERE a.accidentId = :accidentId")
    void deleteByAccidentId(@Param("accidentId") UUID accidentId);
}