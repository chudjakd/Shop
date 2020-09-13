package com.dejvo.Shop.model.helpmodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public String getProductCategoryIfExistIfNotReturnOther(String category){
        if(category==null){
            return "Other";
        }else{
            Optional<String> vystup=categoryofproducts.stream().filter(s -> s.toLowerCase().equals(category.toLowerCase())).findFirst();
            return vystup.orElse("Other");
        }
    }

}
