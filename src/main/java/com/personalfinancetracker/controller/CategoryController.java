package com.personalfinancetracker.controller;



import com.personalfinancetracker.dto.CategoryRequestDTO;
import com.personalfinancetracker.dto.CategoryResponseDTO;
import com.personalfinancetracker.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO dto){
        return new ResponseEntity<>(categoryService.createCategory(dto), HttpStatus.CREATED);
    }
//    getCategoriesByUserId
    @GetMapping("/user/{userId}")
    public ResponseEntity <List<CategoryResponseDTO>> getCategoriesByUserId(@PathVariable Long userId){
        return new ResponseEntity<> (categoryService.getCategoriesByUserId(userId), HttpStatus.OK);
    }
    //    getCategoryById
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.getCategoryById(id),HttpStatus.OK);
    }

//    updateCategory
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO dto) {
        return new ResponseEntity<>(categoryService.updateCategory(id, dto), HttpStatus.OK);
    }

//    dletebyid
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
