package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.db.request.BuyProductRequest;
import com.dejvo.Shop.db.response.BuyProductResponse;

public interface ShoppingInterface {

    public BuyProductResponse buyProduct(BuyProductRequest buyProductRequest);


}
