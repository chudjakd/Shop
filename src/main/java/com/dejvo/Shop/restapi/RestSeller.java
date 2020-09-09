package com.dejvo.Shop.restapi;

import com.dejvo.Shop.db.crudservice.SellerInterface;
import com.dejvo.Shop.db.request.UpdateSellerRequest;
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
            return new ResponseEntity<>(seller1,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/seller/{id}")
    public ResponseEntity updateSeller(@RequestBody UpdateSellerRequest request, @PathVariable("id") Long id){
        if(sellerInterface.readSellerById(id)!=null){
            sellerInterface.updateSeller(request,id);
            return new ResponseEntity<>("Update was succesful",HttpStatus.OK);
        }
        else if(request.getAddress()==null||request.getEmail()==null||request.getName()==null){
            return new ResponseEntity<>("You need insert full body.. something missing",HttpStatus.PRECONDITION_FAILED);
        }
        else{
            return new ResponseEntity<>("Hmm i hope that id doesnt exist",HttpStatus.PRECONDITION_FAILED);
        }
    }
    @DeleteMapping("/seller/{id}")
    public ResponseEntity deleteSellerById(@PathVariable Long id){
        if(sellerInterface.readSellerById(id)!=null){
            return new ResponseEntity<>("Delete was succesfull",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("The seller with that id doesnt exist",HttpStatus.PRECONDITION_FAILED);
        }
    }

}
