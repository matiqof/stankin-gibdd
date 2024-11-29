package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.DrivingLicense;
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
public interface DrivingLicenseRepository extends JpaRepository<DrivingLicense, UUID> {

    /**
     * Найти все водительские удостоверения
     *
     * @return список водительских удостоверений типа {@link DrivingLicense}
     */
    @Transactional
    @Query("SELECT d FROM DrivingLicense d")
    @NonNull
    List<DrivingLicense> findAll();

    /**
     * Найти водительское удостоверение по номеру
     *
     * @param licenseNumber номер водительского удостоверения
     * @return модель водительского удостоверения типа {@link DrivingLicense}
     */
    @Transactional
    @Query("SELECT d FROM DrivingLicense d WHERE d.licenseNumber = :licenseNumber")
    DrivingLicense findByLicenseNumber(@Param("licenseNumber") String licenseNumber);

    /**
     * Проверить существует ли водительское удостоверение с таким номером
     *
     * @param licenseNumber номер водительского удостоверения
     * @return true/false в зависимости от того, существует ли такое водительское удостоверение
     */
    @Transactional
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DrivingLicense d WHERE d.licenseNumber = :licenseNumber")
    boolean existsDrivingLicenseByLicenseNumber(@Param("licenseNumber") String licenseNumber);

    /**
     * Удалить водительское удостоверение по номеру
     *
     * @param licenseNumber номер водительского удостоверения
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM DrivingLicense d WHERE d.licenseNumber = :licenseNumber")
    void deleteByLicenseNumber(@Param("licenseNumber") String licenseNumber);
}