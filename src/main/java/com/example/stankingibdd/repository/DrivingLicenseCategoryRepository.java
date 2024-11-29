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
}
