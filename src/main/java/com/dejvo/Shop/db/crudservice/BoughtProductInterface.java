package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.model.BoughtProduct;

import java.sql.Timestamp;
import java.util.List;

public interface BoughtProductInterface {

    public BoughtProduct getBoughtProductByCustomerIdAndProductId(int customerid, int productid);
    public Integer createBoughtProduct(BoughtProduct boughtProduct);
    public Integer updateCountOfBoughtProduct(BoughtProduct boughtProduct, int count);
    public List<BoughtProduct> getAllBoughtProductsByCustomerId(int customerid);
    public List<BoughtProduct> getAllBoughtProductByProductId(int productid);
    public List<BoughtProduct> getAllBoughtProductByTimeStamp(Timestamp before, Timestamp after);
}
