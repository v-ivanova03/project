package org.example.market.data;

import java.time.LocalDate;

public class NonFoodProduct extends Product {
    public NonFoodProduct(String id, String name, double deliveryPrice, LocalDate expirationDate, int quantity) {
        super(id, name, deliveryPrice, expirationDate, quantity);
    }

    @Override
    public double getMarkupPercentage() {
        return 0.40;
    }
}
