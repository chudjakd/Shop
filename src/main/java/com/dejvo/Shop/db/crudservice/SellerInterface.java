package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.Seller;

import java.util.List;

public interface SellerInterface {

    public void createSeller(Seller seller);
    public Seller readSellerById(Long id);
    public List<Seller> readAllSellers();
    public int updateSeller (Seller seller, Long id);
    public void deleteSeller (Long id);
}
