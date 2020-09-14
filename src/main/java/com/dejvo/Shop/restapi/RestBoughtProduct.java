package com.dejvo.Shop.restapi;


import com.dejvo.Shop.db.crudservice.BoughtProductInterface;
import com.dejvo.Shop.model.BoughtProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestBoughtProduct {
    @Autowired
    BoughtProductInterface boughtProductInterface;

    @GetMapping("/bought-product/customer/{id}")
    public ResponseEntity getAllBoughtProductByCustomerId(@PathVariable int id){
        List<BoughtProduct> allproductfromdatabasebyid=boughtProductInterface.getAllBoughtProductsByCustomerId(id);
        if(allproductfromdatabasebyid!=null){
            return new ResponseEntity<>(allproductfromdatabasebyid, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.PRECONDITION_FAILED);
        }
    }

    @GetMapping("/bought-product/product/{id}")
    public ResponseEntity getAllBoughtProductByProductId(@PathVariable int id){
        List<BoughtProduct> listallboughtproductbyproductid= boughtProductInterface.getAllBoughtProductByProductId(id);
        if(listallboughtproductbyproductid!=null){
            return new ResponseEntity<>(listallboughtproductbyproductid,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Mam taky pocit ze tento product si este nikto nezakupil",HttpStatus.PRECONDITION_FAILED);
        }
    }
}
