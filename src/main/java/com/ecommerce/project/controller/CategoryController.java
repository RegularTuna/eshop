package com.ecommerce.project.controller;


import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDto;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @GetMapping("/public/categories")
    private ResponseEntity<CategoryResponse> getAllCategories(
        @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
        @RequestParam(name="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(name="sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
        @RequestParam(name="sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder )
    {

        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize,sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto savedCategoryDto = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long categoryId){

        CategoryDto deletedCategory= categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategory, HttpStatus.OK);


    }

    @PutMapping("/admin/categories/{categoryId}")
     public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId){

        CategoryDto savedCategoryDto = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(savedCategoryDto, HttpStatus.OK);

     }
}
