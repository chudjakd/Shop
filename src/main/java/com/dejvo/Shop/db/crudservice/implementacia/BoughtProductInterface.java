package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.model.BoughtProduct;

public interface BoughtProductInterface {

    public BoughtProduct getBoughtProductByCustomerIdAndProductId(int customerid, int productid);
    public Integer createBoughtProduct(BoughtProduct boughtProduct);
    public Integer updateCountOfBoughtProduct(BoughtProduct boughtProduct);

}
