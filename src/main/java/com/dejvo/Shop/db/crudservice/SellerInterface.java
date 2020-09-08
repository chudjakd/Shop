package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.Seller;

import java.util.List;

public interface SellerInterface {

    public void createSeller(Seller seller);
    public Seller readSellerById(Long id);
    public List<Seller> readAllSellers();
    // TODO: 8. 9. 2020  Treba spravit update tak aby ked tam pride id ktore neexistuje tak nevysla stadial hodnota 1 akoze update bol uspesny alebo musi vyjst nula
    public int updateSeller (Seller seller, Long id);
    public void deleteSeller (Long id);
}
