package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.AccidentComposition;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface AccidentCompositionRepository extends JpaRepository<AccidentComposition, UUID> {

    /**
     * Найти все составы аварий
     *
     * @return список составов аварий типа {@link AccidentComposition}
     */
    @Transactional
    @Query("SELECT ac FROM AccidentComposition ac")
    @NonNull
    List<AccidentComposition> findAll();

    /**
     * Проверить существует ли состав аварии с такими фильтрами
     *
     * @param accidentId идентификатор аварии
     * @param registrationNumber регистрационный номер транспортного средства
     * @return true/false в зависимости от того, существует ли такой состав аварии
     */
    @Transactional
    @Query("SELECT CASE WHEN COUNT(ac) > 0 THEN true ELSE false END FROM AccidentComposition ac WHERE ac.accidentId = :accidentId and ac.vehicle.registrationNumber = :registrationNumber")
    boolean existsAccidentByAccidentIdAndRegistrationNumber(@Param("accidentId") UUID accidentId, @Param("registrationNumber") String registrationNumber);

    /**
     * Удалить состав аварии по идентификаторам и регистрационному номеру транспортного средства
     *
     * @param accidentId идентификатор аварии
     * @param registrationNumber регистрационный номер транспортного средства
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM AccidentComposition ac WHERE ac.accidentId = :accidentId and ac.vehicle.registrationNumber = :registrationNumber")
    void deleteByAccidentIdAndRegistrationNumber(@Param("accidentId") UUID accidentId, @Param("registrationNumber") String registrationNumber);

    /**
     * Удалить все составы аварий по идентификаторам
     *
     * @param accidentId идентификатор аварии
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM AccidentComposition ac WHERE ac.accidentId = :accidentId")
    void deleteByAccidentId(@Param("accidentId") UUID accidentId);
}
