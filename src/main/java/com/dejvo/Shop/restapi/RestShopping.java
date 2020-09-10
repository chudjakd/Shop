package com.dejvo.Shop.restapi;


import com.dejvo.Shop.db.crudservice.ShoppingInterface;
import com.dejvo.Shop.db.request.BuyProductRequest;
import com.dejvo.Shop.db.response.BuyProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestShopping {
    @Autowired
    ShoppingInterface shoppingInterface;

    @PostMapping("/shopping")
    public ResponseEntity buyProduct(@RequestBody BuyProductRequest buyProductRequest){
        BuyProductResponse response=shoppingInterface.buyProduct(buyProductRequest);
        if(response.isSuccess()){
            return new ResponseEntity<>(response.getErrormessage(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response.getErrormessage(),HttpStatus.PRECONDITION_FAILED);
        }

    }

}
