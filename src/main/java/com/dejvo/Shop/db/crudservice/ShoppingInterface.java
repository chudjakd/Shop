package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.db.request.BuyProductByCardRequest;
import com.dejvo.Shop.db.request.BuyProductRequest;
import com.dejvo.Shop.db.response.BuyProductResponse;
import org.springframework.stereotype.Service;


public interface ShoppingInterface {

    public BuyProductResponse buyProduct(BuyProductRequest buyProductRequest);
    public BuyProductResponse buyProductByCard(BuyProductByCardRequest buyProductByCardRequest);


}
