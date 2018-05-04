package com.gmail.dmytro.backend.data.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Line extends AbstractEntity{

    private int count;

    private Double weight;

    private Double volume;

    @ManyToOne
    private Product product;

    private String comment;

    public Line() {
    }

    public Line(int count, Product product) {
        this.count = count;
        this.product = product;
        this.weight = product.getWeight()*count;
        this.volume = product.getVolume()*count;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
