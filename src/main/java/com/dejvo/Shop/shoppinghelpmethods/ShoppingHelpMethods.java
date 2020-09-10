package com.dejvo.Shop.shoppinghelpmethods;

import org.springframework.stereotype.Component;

@Component
public class ShoppingHelpMethods {

    public boolean haveCustommerEnoughtMoney(double moneyofcustomer, int count, double valueofproduct){

        return (moneyofcustomer-(valueofproduct*count))>=0? true:false;
    }
}
