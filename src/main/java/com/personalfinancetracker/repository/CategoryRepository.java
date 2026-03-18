package com.personalfinancetracker.repository;


import com.personalfinancetracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // findByUserId
    List<Category> findByUserId(Long userId);

    // existsByUserIdAndCategoryName
    boolean existsByUserIdAndCategoryName(Long userId, String categoryName);

}
