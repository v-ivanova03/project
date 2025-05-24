package org.example.market.data;

import java.time.LocalDate;

public class FoodProduct extends Product {
    public FoodProduct(String id, String name, double deliveryPrice, LocalDate expirationDate, int quantity){
        super(id, name, deliveryPrice, expirationDate, quantity);
    }

    @Override
    public double getMarkupPercentage() {
        return 0.20;
    }

}
