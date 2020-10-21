package com.dejvo.Shop.restapi;

import com.dejvo.Shop.db.crudservice.ProductInterface;
import com.dejvo.Shop.db.request.ProductDiscountUpdate;
import com.dejvo.Shop.db.request.UpdateProductRequest;
import com.dejvo.Shop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestProduct {
    @Autowired
    ProductInterface productInterface;

    @GetMapping("/products")
    @Profile({"customer","seller","admin"})
    public ResponseEntity getAllProducts(){
        return new ResponseEntity(productInterface.readAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    @Profile({"customer","seller","admin"})
    public ResponseEntity getProductById(@PathVariable int id){
        Product product= productInterface.readProductById(id);
        if(product!=null){
            return new ResponseEntity<>(product,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product with that id doesnt exist",HttpStatus.PRECONDITION_FAILED);
        }
    }
    // TODO: 9. 9. 2020 @Update(Neviem toto asi nebudem riesit necham to asi tak mozno niekto chce vytvorit product kde nebude count alebo bude cena 0 ze je to free)Nedovolit pridat produkt bez niektoreho parametra
    @PostMapping("/product")
    @Profile({"seller","admin"})
    public ResponseEntity createProduct(@RequestBody Product product){
        Integer key=productInterface.createProduct(product);
        if(key!=null){
            return new ResponseEntity<>(key,HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Hmm something is bad",HttpStatus.PRECONDITION_FAILED);
        }
    }

    @PostMapping("/products")
    @Profile({"seller","admin"})
    public ResponseEntity createMoreProducts(@RequestBody List<Product> products){
        Integer response=productInterface.createMoreProducts(products);
//        Integer response=null;
        if(response!=null) {
            return new ResponseEntity<>("Insert prebehol uspesne", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Insert neprebehol uspesne",HttpStatus.PRECONDITION_FAILED);
        }
    }

    // TODO: 9. 9. 2020 Treba zistit na zaklade coho je mozne upravovat niektore parametre osobitne a niektore len v paroch 
    @PatchMapping("product/{id}")
    @Profile({"seller","admin"})
    public ResponseEntity updateProductWithId(@RequestBody UpdateProductRequest request,@PathVariable("id") int id){
        if(productInterface.readProductById(id)==null){
            return new ResponseEntity<>("Product with that id doesnt exist",HttpStatus.PRECONDITION_FAILED);
        }
        else{
            productInterface.updateProduct(request,id);
            return new ResponseEntity<>("Update was sucessful",HttpStatus.OK);
        }
    }

    @PatchMapping("/products")
    @Profile({"seller","admin"})
    public ResponseEntity updateMoreProducts(@RequestBody List<UpdateProductRequest> requests){
        if(productInterface.updateMoreProducts(requests)!=null){
            return new ResponseEntity<>("Update productov prebehol uspesne",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Hmm nieco selhalo",HttpStatus.PRECONDITION_FAILED);
        }
    }

    @DeleteMapping("product/{id}")
    @Profile({"seller","admin"})
    public ResponseEntity deleteProductWithId(@PathVariable int id){
        if(productInterface.readProductById(id)==null){
            return new ResponseEntity<>("Product with that id doesnt exist",HttpStatus.PRECONDITION_FAILED);
        }
        else{
            productInterface.deleteProduct(id);
            return new ResponseEntity<>("Delete was successful",HttpStatus.OK);
        }
    }
    @PostMapping("/products/discount")
    @Profile({"seller","admin"})
    public ResponseEntity updateProductByDiscount(@RequestBody ProductDiscountUpdate productDiscountUpdate){
        if(productInterface.updateProductByDiscount(productDiscountUpdate)!=null){
            return new ResponseEntity<>("Produkty boli zlavnene",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Todle nevyslo neviem co je zle asi idecka",HttpStatus.PRECONDITION_FAILED);
        }
    }
    @Profile({"customer","seller","admin"})
    @GetMapping("/products/category/{category}")
    public ResponseEntity getAllProductsByCategory(@PathVariable("category") String category){
        List<Product> allproductsbycategory=productInterface.getAllProductsByCategory(category);
        if(allproductsbycategory!=null){
            return new ResponseEntity<>(allproductsbycategory,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Skuste skontrolovat ci ste zadali spravne category",HttpStatus.PRECONDITION_FAILED);
        }
    }
}
