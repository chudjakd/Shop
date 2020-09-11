package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.db.request.UpdateProductRequest;
import com.dejvo.Shop.model.Product;
import com.sun.istack.Nullable;

import java.util.List;

public interface ProductInterface {

    public Integer createProduct(Product product);
    @Nullable
    public Product readProductById(int id);
    public List<Product> readAllProducts();
    public int updateProduct (UpdateProductRequest request, int id);
    public int deleteProduct (int id);
    public Integer updateCountOfProduct(int idofproduct, int newcount );
    public List<Product> getAllProductBySellerId(int sellerid);
    public Integer createMoreProducts(List<Product>products);

}
