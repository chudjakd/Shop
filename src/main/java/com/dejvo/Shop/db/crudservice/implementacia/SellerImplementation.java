package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.SellerInterface;
import com.dejvo.Shop.db.repository.SellerRepository;

import com.dejvo.Shop.db.request.UpdateSellerRequest;
import com.dejvo.Shop.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SellerImplementation implements SellerInterface {

    SellerRepository sellerRepository;

    @Autowired
    public SellerImplementation(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Override
    public Seller createSeller(Seller seller) {
        Integer key= sellerRepository.createSeller(seller);
        return key!=0? sellerRepository.readSellerById(Integer.toUnsignedLong(key)):null;

    }

    @Override
    public Seller readSellerById(Long id) {
    return sellerRepository.readSellerById(id);
    }

    @Override
    public List<Seller> readAllSellers() {
    return sellerRepository.readAllSellers();
    }

    @Override
    public int updateSeller(UpdateSellerRequest request, Long id) {
    return sellerRepository.updateSeller(request,id);
    }

    @Override
    public void deleteSeller(Long id) {
     sellerRepository.deleteSeller(id);
    }
}
