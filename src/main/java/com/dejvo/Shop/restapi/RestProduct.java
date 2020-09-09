package com.dejvo.Shop.restapi;

import com.dejvo.Shop.db.crudservice.ProductInterface;
import com.dejvo.Shop.db.request.UpdateProductRequest;
import com.dejvo.Shop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestProduct {
    @Autowired
    ProductInterface productInterface;

    @GetMapping("/products")
    public ResponseEntity getAllProducts(){
        return new ResponseEntity(productInterface.readAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity getProductById(@PathVariable Long id){
        Product product= productInterface.readProductById(id);
        if(product!=null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product with that id doesnt exist",HttpStatus.PRECONDITION_FAILED);
        }
    }
    // TODO: 9. 9. 2020 Nedovolit pridat produkt bez niektoreho parametra
    @PostMapping("/product")
    public ResponseEntity createProduct(@RequestBody Product product){
        if(productInterface.createProduct(product)!=null){
            return new ResponseEntity<>("Product was created successful",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Hmm something is bad",HttpStatus.PRECONDITION_FAILED);
        }
    }

    // TODO: 9. 9. 2020 Treba zistit na zaklade coho je mozne upravovat niektore parametre osobitne a niektore len v paroch 
    @PatchMapping("product/{id}")
    public ResponseEntity updateProductWithId(@RequestBody UpdateProductRequest request,@PathVariable("id") Long id){
        if(productInterface.readProductById(id)==null){
            return new ResponseEntity<>("Product with that id doesnt exist",HttpStatus.PRECONDITION_FAILED);
        }
        else{
            productInterface.updateProduct(request,id);
            return new ResponseEntity<>("Update was sucessful",HttpStatus.OK);
        }
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity deleteProductWithId(@PathVariable Long id){
        if(productInterface.readProductById(id)==null){
            return new ResponseEntity<>("Product with that id doesnt exist",HttpStatus.PRECONDITION_FAILED);
        }
        else{
            productInterface.deleteProduct(id);
            return new ResponseEntity<>("Delete was successful",HttpStatus.OK);
        }
    }
}
