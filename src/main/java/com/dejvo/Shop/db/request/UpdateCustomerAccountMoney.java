package com.dejvo.Shop.db.request;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class UpdateCustomerAccountMoney {
    @NonNull
    private int customerid;
    @NonNull
    private BigDecimal money;

    public UpdateCustomerAccountMoney(int customerid, @NonNull BigDecimal money) {
        this.customerid = customerid;
        this.money = money;
    }

    public UpdateCustomerAccountMoney() {
    }

    public int getCustomerid() {
        return customerid;
    }

    @NonNull
    public BigDecimal getMoney() {
        return money;
    }
}
