package com.example.stankingibdd.repository;

import com.example.stankingibdd.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    /**
     * Найти категория по названию
     *
     * @param categoryName категория водитеслького удостоверения
     * @return модель категории типа {@link Category}
     */
    @Transactional
    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    Category findByCategoryName(@Param("categoryName") String categoryName);

    /**
     * Проверить существует ли категория водитеслького удостоверения с таким названием
     *
     * @param categoryName категория водитеслького удостоверения
     * @return true/false в зависимости от того, существует ли такая категория водитеслького удостоверения
     */
    @Transactional
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE c.categoryName = :categoryName")
    boolean existsCategoryByCategoryName(@Param("categoryName") String categoryName);
}
