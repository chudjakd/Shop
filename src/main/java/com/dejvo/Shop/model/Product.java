package com.dejvo.Shop.model;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @NonNull
    @Column(name = "seller_id")
    public long sellerId;
    @NonNull
    public String name;
    @NonNull
    public String info;
    @NonNull
    @Column(name = "value",precision = 8,scale = 2)
    public BigDecimal value;
    @NonNull
    public int count;
    @Nullable
    @Column(name = "created_at")
    public Timestamp datetime;

    public Product() {
    }

    public Product( long sellerId, @NonNull String name, @NonNull String info, BigDecimal value, int count, @NonNull Timestamp datetime) {
        this.sellerId = sellerId;
        this.name = name;
        this.info = info;
        this.value = value;
        this.count = count;
        this.datetime = datetime;
    }

    @Nullable
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(@NonNull Timestamp datetime) {
        this.datetime = datetime;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                sellerId == product.sellerId &&
                count == product.count &&
                name.equals(product.name) &&
                info.equals(product.info) &&
                value.equals(product.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sellerId, name, info, value, count);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", sellerId=" + sellerId +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", value=" + value +
                ", count=" + count +
                ", datetime=" + datetime +
                '}';
    }
}
