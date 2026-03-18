package com.personalfinancetracker.service;

import com.personalfinancetracker.dto.CategoryRequestDTO;
import com.personalfinancetracker.dto.CategoryResponseDTO;
import com.personalfinancetracker.entity.Category;
import com.personalfinancetracker.entity.User;
import com.personalfinancetracker.exception.DuplicateResourceException;
import com.personalfinancetracker.exception.ResourceNotFoundException;
import com.personalfinancetracker.repository.CategoryRepository;
import com.personalfinancetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    // create a category...check if category exists by trying t find it with id
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto){
        if(categoryRepository.existsByUserIdAndCategoryName(dto.getUserId(),dto.getCategoryName())){
            throw new DuplicateResourceException("Category with id: " + dto.getUserId() + " " + " name" + dto.getCategoryName() + " exists");
        }
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Category category = Category.builder() // name,type,userid
                .categoryName(dto.getCategoryName())
                .categoryType(dto.getCategoryType())
                .user(user)
                .build();
        Category saved = categoryRepository.save(category);
        return mapToResponseDTO(saved);

    }
    // getCategoryById
    public CategoryResponseDTO getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return mapToResponseDTO(category);
    }
    // getCategoriesByUserId--- returns a list of categories belonging to one user
    public List<CategoryResponseDTO> getCategoriesByUserId(Long userId){
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        return categoryRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    // updateCategory
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto){
        // get category by id
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setCategoryName(dto.getCategoryName());
        category.setCategoryType(dto.getCategoryType());

        Category saved = categoryRepository.save(category);
        return mapToResponseDTO(saved);
    }
    // dletebyid
    public void deleteCategoryById(Long id){
         categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        categoryRepository.deleteById(id);
    }

    // Helper function
    private CategoryResponseDTO mapToResponseDTO(Category category){
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .categoryType(category.getCategoryType())
                .userFullName(category.getUser().getFirstName() + " " + category.getUser().getLastName())
                .userId(category.getUser().getId())
                .build();
    }

}
