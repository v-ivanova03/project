package org.example.market.data;

import java.io.Serializable;

public class SaleItem implements Serializable {
    private Product product;
    private int quantity;
    private double unitPrice;
    private double totalPrice;

    public SaleItem(Product product, int quantity, double unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void recalculateTotalPrice() {
       this.totalPrice = unitPrice * quantity;
    }

    @Override
    public String toString() {
        return product.getName() + " x" + quantity + " = " + totalPrice;
    }
}
