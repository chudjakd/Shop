package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.BoughtProductInterface;
import com.dejvo.Shop.db.repository.BoughtProductRepository;
import com.dejvo.Shop.model.BoughtProduct;
import org.springframework.stereotype.Service;

@Service
public class BoughtProductImplementation implements BoughtProductInterface {

    BoughtProductRepository boughtProductRepository;

    public BoughtProductImplementation(BoughtProductRepository boughtProductRepository) {
        this.boughtProductRepository = boughtProductRepository;
    }

    @Override
    public BoughtProduct getBoughtProductByCustomerIdAndProductId(int customerid, int productid) {
        return boughtProductRepository.selectBoughtProductByCustomerIdAndProductId(customerid,productid);
    }

    @Override
    public Integer createBoughtProduct(BoughtProduct boughtProduct) {
        return boughtProductRepository.createBoughtProduct(boughtProduct);
    }

    @Override
    public Integer updateCountOfBoughtProduct(BoughtProduct boughtProduct) {
        return boughtProductRepository.updateValueOfBoughtProduct(boughtProduct);
    }
}
