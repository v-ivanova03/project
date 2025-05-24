package org.example.market.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Product implements Serializable {
    private String id;
    private String name;
    private double deliveryPrice;
    private LocalDate expirationDate;
    private int quantity;

    public Product(String id, String name, double deliveryPrice, LocalDate expirationDate, int quantity) {
        this.id = id;
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public abstract double getMarkupPercentage();

    public double getSellingPrice(LocalDate currentDate, int daysThreshold, double discountPercent) {
        long daysLeft = ChronoUnit.DAYS.between(currentDate, expirationDate);
        if(daysLeft < 0) return 0;
        double price = deliveryPrice * (1 + getMarkupPercentage());
        if(daysLeft <= daysThreshold){
            price *= (1 - discountPercent);
        }
        return price;
    }

    public boolean isExpired(LocalDate currentDate) {
        return expirationDate.isBefore(currentDate);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", deliveryPrice=" + deliveryPrice +
                ", expirationDate=" + expirationDate +
                ", quantity=" + quantity +
                '}';
    }
}
