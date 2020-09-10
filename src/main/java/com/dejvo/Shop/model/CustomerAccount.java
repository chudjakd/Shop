package com.dejvo.Shop.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "customer_account")
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "customer_id")
    private int customerid;
    @Column(name = "money",precision = 8,scale = 2)
    private BigDecimal money;

    public CustomerAccount() {
    }

    public CustomerAccount(int customerid, BigDecimal money) {
        this.customerid = customerid;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
