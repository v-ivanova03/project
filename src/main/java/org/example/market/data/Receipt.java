package org.example.market.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt implements Serializable {
    private static int counter = 0;
    private int number;
    private Cashier cashier;
    private LocalDateTime dateTime;
    private List<SaleItem> items;
    private double total;

    public Receipt(Cashier cashier, List<SaleItem> items) {
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
        return "Receipt #" + number + "\n" +
                "Cashier: " + cashier.getName() + "\n" +
                "Date: " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n" +
                items.stream().map(SaleItem::toString).collect(Collectors.joining("\n")) + "\n" +
                "Total: " + total;
    }
}
