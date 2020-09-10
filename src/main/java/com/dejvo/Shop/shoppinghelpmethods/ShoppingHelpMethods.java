package com.dejvo.Shop.shoppinghelpmethods;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ShoppingHelpMethods {

    public BigDecimal haveCustommerEnoughtMoney(BigDecimal moneyofcustomer, int count, BigDecimal valueofproduct){
        BigDecimal newMoneyOfCustomer=(moneyofcustomer.subtract(valueofproduct.multiply(BigDecimal.valueOf(count))));
        return newMoneyOfCustomer.compareTo(BigDecimal.ZERO)<=0? newMoneyOfCustomer:null;
    }
}
