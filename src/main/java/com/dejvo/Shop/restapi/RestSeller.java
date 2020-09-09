package com.dejvo.Shop.restapi;

import com.dejvo.Shop.db.crudservice.SellerInterface;
import com.dejvo.Shop.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestSeller {

    @Autowired
    SellerInterface sellerInterface;

    @GetMapping("/sellers")
    public ResponseEntity getAllSellers(){
        List<Seller> sellerList=sellerInterface.readAllSellers();
        return new ResponseEntity<>(sellerList, HttpStatus.OK);
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity getSellerById(@PathVariable Long id){
        Seller seller= sellerInterface.readSellerById(id);
        if(seller!=null){
            return new ResponseEntity<>(seller,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/seller")
    public ResponseEntity createSeller(@RequestBody Seller seller){
        Seller seller1=sellerInterface.createSeller(seller);
        if(seller1!=null){
            return new ResponseEntity<>(seller1,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/seller/{id}")
    public ResponseEntity updateSeller(@RequestBody Seller seller, @PathVariable("id") Long id){
        if(sellerInterface.updateSeller(seller,id)==1){
            return new ResponseEntity<>("Update was succesful",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

}
