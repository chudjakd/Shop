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
    public Product readProductById(int id) {
        return productRepository.readProductById(id);
    }

    @Override
    public List<Product> readAllProducts() {
        return productRepository.readAllProducts();
    }

    @Override
    public int updateProduct(UpdateProductRequest request, int id) {
        return productRepository.updateProduct(id,request);
    }

    @Override
    public int deleteProduct(int id) {
    return productRepository.deleteProduct(id);
    }

    @Override
    public Integer updateCountOfProduct(int idofproduct, int newcount) {
        return productRepository.updateProductCount(idofproduct, newcount);
    }
}
