package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.db.request.UpdateProductRequest;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.model.Seller;
import com.sun.istack.Nullable;

import java.util.List;

public interface ProductInterface {

    public Integer createProduct(Product product);
    @Nullable
    public Product readProductById(Long id);
    public List<Product> readAllProducts();
    public int updateProduct (UpdateProductRequest request, Long id);
    public int deleteProduct (Long id);

}
