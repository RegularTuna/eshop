package com.ecommerce.project.controller;


import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.ProductDto;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long categoryId){
       ProductDto savedProductDto =  productService.addProduct(categoryId, productDto);
       return new ResponseEntity<>(savedProductDto, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE )Integer pageSize ,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY)String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR ) String sortOrder
    ){


        ProductResponse productResponse = productService.getAllProducts(pageNumber,pageSize,sortBy,sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }


    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId,
                                                                 @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE )Integer pageSize ,
                                                                 @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY)String sortBy,
                                                                 @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR ) String sortOrder){
        ProductResponse productResponse = productService.searchByCategory(categoryId,pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword (@PathVariable String keyword,
                                                                @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                                @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE )Integer pageSize ,
                                                                @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY)String sortBy,
                                                                @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR ) String sortOrder){
       ProductResponse productResponse = productService.searchProductByKeyword(keyword, pageNumber,pageSize,sortBy,sortOrder);

        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }


    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Long productId){
        ProductDto updatedProductDto = productService.updateProduct(productId, productDto);

        return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDto> deleteProduct (@PathVariable Long productId){

        ProductDto deletedProduct = productService.deleteProduct(productId);

        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDto> updateProductImage(@PathVariable Long productId, @RequestParam("image")MultipartFile image) throws IOException {
        ProductDto updatedProduct = productService.updateProductImage(productId, image);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

}
