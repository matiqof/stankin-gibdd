package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.DrivingLicenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface DrivingLicenseCategoryRepository extends JpaRepository<DrivingLicenseCategory, UUID> {

    /**
     * Удалить связь категории и водительского удостоверения
     *
     * @param licenseNumber номер водительского удостоверения
     * @param categoryName категория водитеслького удостоверения
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM DrivingLicenseCategory d WHERE d.drivingLicense.licenseNumber = :licenseNumber AND d.category.categoryName = :categoryName")
    void deleteByLicenseNumberAndCategoryName(@Param("licenseNumber") String licenseNumber, @Param("categoryName") String categoryName);

    /**
     * Удалить все связи по водительскому удостоверению
     *
     * @param licenseNumber номер водительского удостоверения
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM DrivingLicenseCategory d WHERE d.drivingLicense.licenseNumber = :licenseNumber")
    void deleteByLicenseNumber(@Param("licenseNumber") String licenseNumber);

    /**
     * Проверить существует ли сзязь с таким номером водительского удостоверения
     *
     * @param licenseNumber номер водительского удостоверения
     * @return true/false в зависимости от того, существует ли такое водительское удостоверение
     */
    @Transactional
    @Query("SELECT CASE WHEN COUNT(d) > 1 THEN true ELSE false END FROM DrivingLicense d WHERE d.licenseNumber = :licenseNumber")
    boolean existsAnyDrivingLicenseCategoryByLicenseNumber(@Param("licenseNumber") String licenseNumber);
}
