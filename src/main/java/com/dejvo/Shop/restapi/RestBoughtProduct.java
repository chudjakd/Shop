package com.dejvo.Shop.restapi;


import com.dejvo.Shop.db.crudservice.BoughtProductInterface;
import com.dejvo.Shop.db.request.GetProductsByTimestamp;
import com.dejvo.Shop.model.BoughtProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestBoughtProduct {
    @Autowired
    BoughtProductInterface boughtProductInterface;

    @GetMapping("/bought-product/customer/{id}")
    @Profile({"customer","seller","admin"})
    public ResponseEntity getAllBoughtProductByCustomerId(@PathVariable int id){
        List<BoughtProduct> allproductfromdatabasebyid=boughtProductInterface.getAllBoughtProductsByCustomerId(id);
        if(allproductfromdatabasebyid!=null){
            return new ResponseEntity<>(allproductfromdatabasebyid, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.PRECONDITION_FAILED);
        }
    }


    @GetMapping("/bought-product/product/{id}")
    @Profile({"customer","seller","admin"})
    public ResponseEntity getAllBoughtProductByProductId(@PathVariable int id){
        List<BoughtProduct> listallboughtproductbyproductid= boughtProductInterface.getAllBoughtProductByProductId(id);
        if(listallboughtproductbyproductid!=null){
            return new ResponseEntity<>(listallboughtproductbyproductid,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Mam taky pocit ze tento product si este nikto nezakupil",HttpStatus.PRECONDITION_FAILED);
        }
    }

    @PostMapping("/bought-product/time")
    @Profile({"customer","seller","admin"})
    public ResponseEntity getAllProductByTime(@RequestBody GetProductsByTimestamp getProductsByTimestamp){
        List<BoughtProduct> allProductByTimestamp= boughtProductInterface.getAllBoughtProductByTimeStamp(getProductsByTimestamp.getBefore(),getProductsByTimestamp.getAfter());
        if(allProductByTimestamp!=null){
            return new ResponseEntity<>(allProductByTimestamp,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Tak toto nevyslo",HttpStatus.PRECONDITION_FAILED);
        }
    }
}
