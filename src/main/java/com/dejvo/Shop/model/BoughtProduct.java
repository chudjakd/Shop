package com.dejvo.Shop.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;


@Entity
@Table(name = "bought_product")
public class BoughtProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "customer_id")
    private int customerid;
    @Column(name = "product_id")
    private int productid;
    @Column(name = "count")
    private int count;
    @Column(name = "bought_at")
    private Timestamp boughtat;

    public BoughtProduct() {
    }

    public BoughtProduct(int customerid, int productid, int count, Timestamp boughtat) {
        this.customerid = customerid;
        this.productid = productid;
        this.count = count;
        this.boughtat = Timestamp.from(Instant.now());
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Timestamp getBoughtat() {
        return boughtat;
    }

    public void setBoughtat(Timestamp boughtat) {
        this.boughtat = boughtat;
    }
}
