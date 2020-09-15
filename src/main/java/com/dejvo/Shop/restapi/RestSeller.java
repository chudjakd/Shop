package com.dejvo.Shop.restapi;

import com.dejvo.Shop.db.crudservice.ProductInterface;
import com.dejvo.Shop.db.crudservice.SellerInterface;
import com.dejvo.Shop.db.request.UpdateSellerRequest;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.model.Seller;
import com.dejvo.Shop.model.SellerWithStatistic;
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
    @Autowired
    ProductInterface productInterface;

    @GetMapping("/seller/{id}/products")
    public ResponseEntity getAllProductBySellerId(@PathVariable int id){
        List<Product> listOfAllProducts=productInterface.getAllProductBySellerId(id);
        if(listOfAllProducts==null){
            return new ResponseEntity<>(null,HttpStatus.PRECONDITION_FAILED);
        }else{
            return new ResponseEntity<>(listOfAllProducts,HttpStatus.OK);
        }
    }

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
        Integer sellerKey=sellerInterface.createSeller(seller);
        if(sellerKey!=null){
            return new ResponseEntity<>(sellerKey,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/seller/{id}")
    public ResponseEntity updateSeller(@RequestBody UpdateSellerRequest request, @PathVariable("id") Long id){
        if(sellerInterface.readSellerById(id)!=null){
            if(sellerInterface.updateSeller(request,id)==0){
                return new ResponseEntity<>("You need insert full body.. something missing",HttpStatus.PRECONDITION_FAILED);
            }
            else {
                return new ResponseEntity<>("Update was succesful", HttpStatus.OK);
            }

        }
        else{
            return new ResponseEntity<>("Hmm i hope that id doesnt exist",HttpStatus.PRECONDITION_FAILED);
        }
    }
    @DeleteMapping("/seller/{id}")
    public ResponseEntity deleteSellerById(@PathVariable Long id){
        if(sellerInterface.readSellerById(id)!=null){
            sellerInterface.deleteSeller(id);
            return new ResponseEntity<>("Delete was succesfull",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("The seller with that id doesnt exist",HttpStatus.PRECONDITION_FAILED);
        }
    }

    @GetMapping("/seller/mostpopular")
    public ResponseEntity getMostPopularSellers(){
        List<SellerWithStatistic> sellerWithStatistics=sellerInterface.getMostPopularSeller();
        if(sellerWithStatistics!=null){
            return new ResponseEntity<>(sellerWithStatistics,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Hmm toto nevyslo",HttpStatus.PRECONDITION_FAILED);
        }
    }

    @GetMapping("/seller/bestseller")
    public ResponseEntity getAllSellersSortedByMostSellProducts(){
        List<SellerWithStatistic> sellerWithStatistics=sellerInterface.getSellerSortedByMostSellProducts();
        if(sellerWithStatistics!=null){
            return new ResponseEntity<>(sellerWithStatistics,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Hmm toto nevyslo",HttpStatus.PRECONDITION_FAILED);
        }
    }


}
