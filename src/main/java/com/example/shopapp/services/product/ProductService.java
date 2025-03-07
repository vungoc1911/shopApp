package com.example.shopapp.services.product;

import com.example.shopapp.dto.ProductDTO;
import com.example.shopapp.model.Product;
import com.example.shopapp.repositories.CategoryRepository;
import com.example.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws Exception {
        categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(
                () -> new DateTimeException("Category not found"));
        return null;
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return null;
    }

    @Override
    public Page<Product> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        return null;
    }

    @Override
    public void deleteProduct(long id) {

    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
