package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.BoughtProductInterface;
import com.dejvo.Shop.db.crudservice.CustomerInterface;
import com.dejvo.Shop.db.crudservice.ProductInterface;
import com.dejvo.Shop.db.crudservice.ShoppingInterface;
import com.dejvo.Shop.db.request.BuyProductRequest;
import com.dejvo.Shop.db.response.BuyProductResponse;
import com.dejvo.Shop.model.BoughtProduct;
import com.dejvo.Shop.shoppinghelpmethods.ShoppingHelpMethods;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
@Component
public class ShoppingImplementacia implements ShoppingInterface {

    ProductInterface productInterface;
    CustomerInterface customerInterface;
    ShoppingHelpMethods shoppingHelpMethods;
    CustomerAccountImplementacia customerAccountImplementacia;
    BoughtProductInterface boughtProductInterface;

    public ShoppingImplementacia(ProductInterface productInterface, CustomerInterface customerInterface, ShoppingHelpMethods shoppingHelpMethods, CustomerAccountImplementacia customerAccountImplementacia, BoughtProductInterface boughtProductInterface) {
        this.productInterface = productInterface;
        this.customerInterface = customerInterface;
        this.shoppingHelpMethods = shoppingHelpMethods;
        this.customerAccountImplementacia = customerAccountImplementacia;
        this.boughtProductInterface = boughtProductInterface;
    }

    /*
        Nie je to moc zrozumitelne tak radsej komentik, 1. if nam zistuje ci dany customer a product ktory pride v requeste existuju
        2. if nam zistuje ci customer ktory chce nakupit urcity pocet produktov ma dostatocny pocet penazi
        3. if nam zistuje ci je pocet produktov na sklade vacsi alebo rovny tomu ktory prisiel z requestu
        4. if nam zistuje ci uz nahodou customer nema produkt ktory chce kupit ak ma tak len pripocitame k tomu existujucemu poctu ten pocet ktory chce nakupit
         */
    @Override
    public BuyProductResponse buyProduct(BuyProductRequest buyProductRequest) {

        Integer customerid=buyProductRequest.getCustomerid();
        Integer productid=buyProductRequest.getProductid();
        Integer countofbuyingproduct=buyProductRequest.getCount();
        // 1 if
        if( productInterface.readProductById(productid)!=null  &&  customerInterface.readCustomerById(customerid.longValue())!=null ) {
            //2 if
            Double newMoneyOfCustomer=shoppingHelpMethods.haveCustommerEnoughtMoney(customerAccountImplementacia.getMoneyByCustomerId(customerid)
                                                                                    ,buyProductRequest.getCount()
                                                                                    ,productInterface.readProductById(productid).getValue());
            if(newMoneyOfCustomer!=null)
            {

                Integer countofproductatwarehouse=productInterface.readProductById(productid).getCount();
                // 3. if
                if(countofproductatwarehouse>=countofbuyingproduct){

                    Integer newCountOfProduct=countofproductatwarehouse-countofbuyingproduct;   //Odpocitanie poctu ktory customer nakupil od celkoveho poctu produktu na sklade
                    productInterface.updateCountOfProduct(productid,newCountOfProduct);         // Update poctu produktov na sklade kedze doslo k uspesnemu nakupu
                    customerAccountImplementacia.updateMoneyOfCustomerAccount(newMoneyOfCustomer,customerid);   //Update penazi customera ktory uspesne nakupil
                    BoughtProduct boughtProductFromDatabase=boughtProductInterface.getBoughtProductByCustomerIdAndProductId(customerid,productid);
                    // 4. if
                    if(boughtProductFromDatabase!=null){
                        boughtProductInterface.updateCountOfBoughtProduct(boughtProductFromDatabase,newCountOfProduct);
                        return new BuyProductResponse(true,"Uspesne pripocitanie poctu produktov kedze customer uz dany produkt raz nakupoval");
                    }else{
                        BoughtProduct newBoughtOfProduct= new BoughtProduct(customerid,productid,countofbuyingproduct, Timestamp.from(Instant.now()));
                        boughtProductInterface.createBoughtProduct(newBoughtOfProduct);
                        return new BuyProductResponse(true,"Uspesny nakup");
                    }

                }else{
                    return new BuyProductResponse(false,"Nedostatocny pocet produktov na sklade");
                }

            }else{
                return new BuyProductResponse(false,"Nedostatok penez");
            }

        }else {
            return new BuyProductResponse(false,"Zakaznik alebo product neexistuje");
        }
    }
}
