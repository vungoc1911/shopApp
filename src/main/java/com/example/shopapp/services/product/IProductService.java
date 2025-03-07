package com.example.shopapp.services.product;

import com.example.shopapp.dto.ProductDTO;
import com.example.shopapp.model.Product;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(long id) throws Exception;
    Page<Product> getAllProducts(String keyword,
                                 Long categoryId, PageRequest pageRequest);
    Product updateProduct(long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existsByName(String name);
}
