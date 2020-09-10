package com.dejvo.Shop.shoppinghelpmethods;

import org.springframework.stereotype.Component;

@Component
public class ShoppingHelpMethods {

    public Double haveCustommerEnoughtMoney(double moneyofcustomer, int count, double valueofproduct){
        Double newMoneyOfCustomer=(moneyofcustomer-(valueofproduct*count));
        return newMoneyOfCustomer>=0? (moneyofcustomer-(valueofproduct*count)):null;
    }
}
