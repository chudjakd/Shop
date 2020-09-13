package com.dejvo.Shop.model.helpmodels;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory {

    private List<String> categoryofproducts= new ArrayList<>();

    public ProductCategory() {
        List<String> categoryofproducts= new ArrayList<>();
        categoryofproducts.add("Clothes");
        categoryofproducts.add("Food stuff");
        categoryofproducts.add("Toys");
        categoryofproducts.add("Other");
        this.categoryofproducts = categoryofproducts;
    }

    public List<String> getCategoryofproducts() {
        return categoryofproducts;
    }
}
