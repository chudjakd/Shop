package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.*;
import com.dejvo.Shop.db.request.BuyProductRequest;
import com.dejvo.Shop.db.response.BuyProductResponse;
import com.dejvo.Shop.model.BoughtProduct;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.shoppinghelpmethods.ShoppingHelpMethods;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
@Service
public class ShoppingImplementacia implements ShoppingInterface {

    ProductInterface productInterface;
    CustomerInterface customerInterface;
    ShoppingHelpMethods shoppingHelpMethods;
    CustomerAccountInterface customerAccountInterface;
    BoughtProductInterface boughtProductInterface;

    public ShoppingImplementacia(ProductInterface productInterface, CustomerInterface customerInterface, ShoppingHelpMethods shoppingHelpMethods, CustomerAccountImplementacia customerAccountImplementacia, BoughtProductInterface boughtProductInterface) {
        this.productInterface = productInterface;
        this.customerInterface = customerInterface;
        this.shoppingHelpMethods = shoppingHelpMethods;
        this.customerAccountInterface = customerAccountImplementacia;
        this.boughtProductInterface = boughtProductInterface;
    }

    /*
        Nie je to moc zrozumitelne tak radsej komentik, 1. if nam zistuje  product ktory pride v requeste existuje
        2. if nam zistuje ci dany customer existuje
        3. if nam zistuje ci customer ktory chce nakupit urcity pocet produktov ma dostatocny pocet penazi
        4. if nam zistuje ci je pocet produktov na sklade vacsi alebo rovny tomu ktory prisiel z requestu
        5. if nam zistuje ci uz nahodou customer nema produkt ktory chce kupit ak ma tak len pripocitame k tomu existujucemu poctu ten pocet ktory chce nakupit
         */
    @Override
    public BuyProductResponse buyProduct(BuyProductRequest buyProductRequest) {

        Integer customerid = buyProductRequest.getCustomerid();
        Integer productid = buyProductRequest.getProductid();
        Integer countofbuyingproduct = buyProductRequest.getCount();

        // 1 if
        if (productInterface.readProductById(productid) == null) {
            return new BuyProductResponse(false, "Product s ID:" + productid + " neexistuje");
        }

        //2 if
        if (customerAccountInterface.getCustomerByIdOfCustomer(customerid) == null) {
            return new BuyProductResponse(false, "Customer s ID:" + customerid + " neexistuje");
        }

        // 3. if
        Integer countofproductatwarehouse = productInterface.readProductById(productid).getCount();
        if (countofproductatwarehouse < countofbuyingproduct) {
            return new BuyProductResponse(false, "Nie je dostatocny pocet produktov ");
        }

        Product product = productInterface.readProductById(productid);
        BigDecimal productvalue = product.getValue();
        BigDecimal newMoneyOfCustomer = shoppingHelpMethods.haveCustommerEnoughtMoney(customerAccountInterface.getMoneyByCustomerId(customerid)
                , buyProductRequest.getCount()
                , productvalue);
        // 4. if
        if (newMoneyOfCustomer != null) {
            Integer newCountOfProductAtWarehouse = countofproductatwarehouse - countofbuyingproduct;   //Odpocitanie poctu ktory customer nakupil od celkoveho poctu produktu na sklade
            productInterface.updateCountOfProduct(productid, newCountOfProductAtWarehouse);         // Update poctu produktov na sklade kedze doslo k uspesnemu nakupu
            customerAccountInterface.updateMoneyOfCustomerAccount(newMoneyOfCustomer, customerid);   //Update penazi customera ktory uspesne nakupil
            BoughtProduct boughtProductFromDatabase = boughtProductInterface.getBoughtProductByCustomerIdAndProductId(customerid, productid);

            // 5. if
            if (boughtProductFromDatabase != null) {
                Integer newCountOfProductInBoughtProduct = boughtProductFromDatabase.getCount() + countofbuyingproduct; //Ak product uz dany zakaznik niekedy nakupoval pridame uz k existujucemu poctu productu
                boughtProductInterface.updateCountOfBoughtProduct(boughtProductFromDatabase, newCountOfProductInBoughtProduct);
                return new BuyProductResponse(true, "Uspesne pripocitanie poctu produktov kedze customer uz dany produkt raz nakupoval");
            } else {
                BoughtProduct newBoughtOfProduct = new BoughtProduct(customerid, productid, countofbuyingproduct, Timestamp.from(Instant.now()));
                boughtProductInterface.createBoughtProduct(newBoughtOfProduct);
                return new BuyProductResponse(true, "Uspesny nakup");
            }

        }else{
            return new BuyProductResponse(false, "Customer s ID:"+customerid+" nema dostatok penazi");

        }
    }
    }
