package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.ProductInterface;
import com.dejvo.Shop.db.repository.ProductRepository;
import com.dejvo.Shop.db.request.UpdateProductRequest;
import com.dejvo.Shop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImplementation implements ProductInterface {

    ProductRepository productRepository;
    @Autowired
    public ProductImplementation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Integer createProduct(Product product) {
        return productRepository.createProduct(product);
    }

    @Override
    public Product readProductById(Long id) {
        return productRepository.readProductById(id);
    }

    @Override
    public List<Product> readAllProducts() {
        return productRepository.readAllProducts();
    }

    @Override
    public int updateProduct(UpdateProductRequest request, Long id) {
        return productRepository.updateProduct(id,request);
    }

    @Override
    public int deleteProduct(Long id) {
    return productRepository.deleteProduct(id);
    }
}
