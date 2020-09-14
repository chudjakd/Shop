package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.*;
import com.dejvo.Shop.db.request.BuyProductByCardRequest;
import com.dejvo.Shop.db.request.BuyProductRequest;
import com.dejvo.Shop.db.response.BuyProductResponse;
import com.dejvo.Shop.model.BoughtProduct;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.helpmethods.shopping.ShoppingHelpMethods;
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
        Nie je to moc zrozumitelne tak radsej komentik, 1. if nam zistuje ci product ktory pride v requeste existuje
        2. if nam zistuje ci dany customer existuje
        3. if nam zistuje ci customer ktory chce nakupit urcity pocet produktov ma dostatocny pocet penazi
        4. if nam zistuje ci je pocet produktov na sklade vacsi alebo rovny tomu ktory prisiel z requestu
        5. if nam zistuje ci uz nahodou customer nema produkt ktory chce kupit ak ma tak len pripocitame k tomu existujucemu poctu ten pocet ktory chce nakupit
         */
    @Override
    public BuyProductResponse buyProduct(BuyProductRequest buyProductRequest) {

        int customerid = buyProductRequest.getCustomerid();
        int productid = buyProductRequest.getProductid();
        int countofbuyingproduct = buyProductRequest.getCount();

        // 1 if
        if (productInterface.readProductById(productid) == null) {
            return new BuyProductResponse(false, "Product s ID:" + productid + " neexistuje");
        }

        //2 if
        if (customerAccountInterface.getCustomerByIdOfCustomer(customerid) == null) {
            return new BuyProductResponse(false, "Customer s ID:" + customerid + " neexistuje");
        }

        // 3. if
        int countofproductatwarehouse = productInterface.readProductById(productid).getCount();
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
            int newCountOfProductAtWarehouse = countofproductatwarehouse - countofbuyingproduct;   //Odpocitanie poctu ktory customer nakupil od celkoveho poctu produktu na sklade
            productInterface.updateCountOfProduct(productid, newCountOfProductAtWarehouse);         // Update poctu produktov na sklade kedze doslo k uspesnemu nakupu
            customerAccountInterface.updateMoneyOfCustomerAccount(newMoneyOfCustomer, customerid);   //Update penazi customera ktory uspesne nakupil
            BoughtProduct boughtProductFromDatabase = boughtProductInterface.getBoughtProductByCustomerIdAndProductId(customerid, productid);

            // 5. if
            if (boughtProductFromDatabase != null) {
                int newCountOfProductInBoughtProduct = boughtProductFromDatabase.getCount() + countofbuyingproduct; //Ak product uz dany zakaznik niekedy nakupoval pridame uz k existujucemu poctu productu
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


    // 1 if kontroluje ci je cislo karty v spravnom formate
    //2 if kontroluje ci je datetime karty v spravnom formate
    // 3 if kontroluje ci je safecode karty v spravnom formate
    // 4 if kontroluje ci produkt s danym id existuje
    // 5 if kontroluje ci customer s danym id existuje
    //6 if kontroluje ci je dostatocny pocet produktov
    // 7 if kontroluje ci nahodou uz tento customer nekupoval niekedy product s tymto id ak ano tak to zapise uz k existujucemu poctu
    @Override
    public BuyProductResponse buyProductByCard(BuyProductByCardRequest buyProductByCardRequest) {
        String cardnumber=buyProductByCardRequest.getCardnumber();
        String datecard=buyProductByCardRequest.getDatecard();
        String safenumber=buyProductByCardRequest.getSafecode();
        int productid=buyProductByCardRequest.getProductid();
        int customerid=buyProductByCardRequest.getCustomerid();
        int countofbuyingproduct=buyProductByCardRequest.getCountofbuyingproduct();

        String regexforcardnumber="(\\d{4})(\\s)(\\d{4})(\\s)(\\d{4})(\\s)(\\d{4})";
        String regexfordatecard="(\\d{2})[/](\\d{2})";
        String regexforsafenumber="(\\d{3})";
        // 1 if
        if((!cardnumber.matches(regexforcardnumber))||(cardnumber.length()!=19)){
            return new BuyProductResponse(false,"Skontrolujte prosim card number");
        }
        // 2 if
        if((!datecard.matches(regexfordatecard))||(datecard.length()!=5)){
            return new BuyProductResponse(false,"Skontrolujte prosim datecard");
        }

        // 3 if
        if((!safenumber.matches(regexforsafenumber))||(safenumber.length()!=3)){
            return new BuyProductResponse(false,"Skontrolujte prosim safe number");
        }

        // 4 if
        if (productInterface.readProductById(productid) == null) {
            return new BuyProductResponse(false, "Product s ID:" + productid + " neexistuje");
        }

        // 5 if
        if (customerAccountInterface.getCustomerByIdOfCustomer(customerid) == null) {
            return new BuyProductResponse(false, "Customer s ID:" + customerid + " neexistuje");
        }

        // 6. if
        int countofproductatwarehouse = productInterface.readProductById(productid).getCount();
        if (countofproductatwarehouse < countofbuyingproduct) {
            return new BuyProductResponse(false, "Nie je dostatocny pocet produktov ");
        }

        int newCountOfProductAtWarehouse = countofproductatwarehouse - countofbuyingproduct;   //Odpocitanie poctu ktory customer nakupil od celkoveho poctu produktu na sklade
        productInterface.updateCountOfProduct(productid, newCountOfProductAtWarehouse);         // Update poctu produktov na sklade kedze doslo k uspesnemu nakupu
        BoughtProduct boughtProductFromDatabase = boughtProductInterface.getBoughtProductByCustomerIdAndProductId(customerid, productid);

        // 7. if
        if (boughtProductFromDatabase != null) {
            int newCountOfProductInBoughtProduct = boughtProductFromDatabase.getCount() + countofbuyingproduct; //Ak product uz dany zakaznik niekedy nakupoval pridame uz k existujucemu poctu productu
            boughtProductInterface.updateCountOfBoughtProduct(boughtProductFromDatabase, newCountOfProductInBoughtProduct);
            return new BuyProductResponse(true, "Uspesne pripocitanie poctu produktov kedze customer uz dany produkt raz nakupoval");
        } else {
            BoughtProduct newBoughtOfProduct = new BoughtProduct(customerid, productid, countofbuyingproduct, Timestamp.from(Instant.now()));
            boughtProductInterface.createBoughtProduct(newBoughtOfProduct);
            return new BuyProductResponse(true, "Uspesny nakup");
        }

    }
}
