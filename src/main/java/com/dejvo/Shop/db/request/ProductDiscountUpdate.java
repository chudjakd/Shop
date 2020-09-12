package com.dejvo.Shop.db.request;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public class ProductDiscountUpdate {
    @NonNull
    private List<Integer> idofproducts;
    @NonNull
    private BigDecimal percentofdiscount;

    public ProductDiscountUpdate() {
    }

    public ProductDiscountUpdate(@NonNull List<Integer> idofproducts, @NonNull BigDecimal percentofdiscount) {
        this.idofproducts = idofproducts;
        this.percentofdiscount = percentofdiscount;
    }

    @NonNull
    public List<Integer> getIdofproducts() {
        return idofproducts;
    }

    @NonNull
    public BigDecimal getPercentofdiscount() {
        return percentofdiscount;
    }
}
