package com.personalfinancetracker.dto;

import com.personalfinancetracker.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDTO {

    @NotBlank(message = "category name is required")
    private String categoryName;

    @NotNull(message = "category type is required")
    private Category.CategoryType categoryType;

    @NotNull(message = "user id is required")
    private Long userId;
}
