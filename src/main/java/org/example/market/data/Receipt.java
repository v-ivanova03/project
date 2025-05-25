package org.example.market.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Receipt implements Serializable {
    private static int counter = 0;
    private int number;
    private Cashier cashier;
    private LocalDateTime dateTime;
    private List<SaleItem> items;
    private double total;

    public Receipt(Cashier cashier, List<SaleItem> itmes) {
        this.number = ++counter;
        this.cashier = cashier;
        this.dateTime = LocalDateTime.now();
        this.items = items;
        this.total = items.stream().mapToDouble(SaleItem::getTotalPrice).sum();
    }

    public int getNumber() {
        return number;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Receipt #").append(number).append("\n");
        sb.append("Cashier: ").append(cashier.getName()).append("\n");
        sb.append("Date: ").append(dateTime).append("\n");
        for (SaleItem item : items) {
            sb.append(item).append("\n");
        }
        sb.append("Total: ").append(total);
        return sb.toString();
    }
}
