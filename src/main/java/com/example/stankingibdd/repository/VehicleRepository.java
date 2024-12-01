package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.Vehicle;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    /**
     * Найти все транспортные средства
     *
     * @return список транспортных средств типа {@link Vehicle}
     */
    @Transactional
    @Query("SELECT v FROM Vehicle v")
    @NonNull
    List<Vehicle> findAll();

    /**
     * Найти транспортное средство по регистрационному номеру
     *
     * @param registrationNumber регистрационный номер транспортного средства
     * @return модель транспортного средства типа {@link Vehicle}
     */
    @Transactional
    @Query("SELECT v FROM Vehicle v WHERE v.registrationNumber = :registrationNumber")
    Vehicle findByRegistrationNumber(@Param("registrationNumber") String registrationNumber);

    /**
     * Проверить существует ли транспортное средство с таким регистрационным номером
     *
     * @param registrationNumber регистрационный номер транспортного средства
     * @return true/false в зависимости от того, существует ли такое транспортное средство
     */
    @Transactional
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Vehicle v WHERE v.registrationNumber = :registrationNumber")
    boolean existsVehicleByRegistrationNumber(@Param("registrationNumber") String registrationNumber);

    /**
     * Удалить транспортное средство по регистрационному номеру
     *
     * @param registrationNumber регистрационный номер транспортного средства
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Vehicle v WHERE v.registrationNumber = :registrationNumber")
    void deleteByRegistrationNumber(@Param("registrationNumber") String registrationNumber);
}
