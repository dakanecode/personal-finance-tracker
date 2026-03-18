package com.personalfinancetracker.dto;

import com.personalfinancetracker.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDTO {
    private Long id;
    private String categoryName;
    private Category.CategoryType categoryType;
    private Long userId;
    private String userFullName;
}
