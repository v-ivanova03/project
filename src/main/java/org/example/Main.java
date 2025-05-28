package org.example;

import org.example.market.data.*;
import org.example.market.exception.InsufficientQuantityException;
import org.example.market.service.ReceiptService;
import org.example.market.service.StoreService;
import org.example.market.service.impl.ReceiptServiceImpl;
import org.example.market.service.impl.StoreServiceImpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        StoreService store = new StoreServiceImpl();
        ReceiptService receiptService = new ReceiptServiceImpl();

        // Add cashier
        Cashier cashier = new Cashier("C02", "Borislav Ivanov", 1350);
        CashRegister register = new CashRegister("R02", cashier);
        store.addCashier(cashier);
        store.addCashRegister(register);

        Cashier cashier2 = new Cashier("C01", "Viktoria Ivanova", 1300);
        CashRegister register2 = new CashRegister("R01", cashier2);
        store.addCashRegister(register2);
        store.addCashier(cashier2);

        // Loading products
        Product bread = new FoodProduct("F10", "Whole Wheat Bread", 0.80, LocalDate.now().plusDays(4), 20);
        Product juice = new FoodProduct("F11", "Orange Juice", 1.50, LocalDate.now().plusDays(6), 15);
        Product shampoo = new NonFoodProduct("NF20", "Herbal Shampoo", 5.20, LocalDate.now().plusDays(365), 8);
        Product expiredMeat = new FoodProduct("F99", "Expired Sausage", 3.90, LocalDate.now().minusDays(2), 5);
        Product cheese = new FoodProduct("P04", "Cheese", 2.5, LocalDate.now().plusDays(7), 8);
        Product cocaCola = new NonFoodProduct("P35", "Coca-Cola", 1.8, LocalDate.now().plusDays(87), 10);

        store.addProduct(bread);
        store.addProduct(juice);
        store.addProduct(shampoo);
        store.addProduct(expiredMeat);
        store.addProduct(cheese);
        store.addProduct(cocaCola);

        // Shopping
        Map<String, Integer> cart = new HashMap<>();
        cart.put("F10", 3); // Bread
        cart.put("F11", 2); // Juice
        cart.put("NF20", 1); // Shampoo
        cart.put("F99", 2); // Expired – will be ignored

        Map<String, Integer> cart2 = new HashMap<>();
        cart2.put("P04", 3); // Cheese
        cart2.put("P35", 2); // Soap

        try {
            Receipt receipt = store.sell(cart, register);
            System.out.println("\nNote issued:");
            System.out.println(receipt);
            Receipt receipt2 = store.sell(cart2, register2);
            System.out.println(receipt2);

            // Save to text file
            receiptService.saveToFile(receipt);
            receiptService.saveToFile(receipt2);

            // Reading from a text file
            System.out.println("\nText file content:");
            System.out.println(receiptService.readFromFile(receipt.getNumber()));

            // Serialization and deserialization
            receiptService.serializeReceipt(receipt);
            receiptService.serializeReceipt(receipt2);
            System.out.println("\nDeserialized receipt:");
            Receipt restored = receiptService.deserializeReceipt(receipt.getNumber());
            System.out.println(restored);

        } catch (InsufficientQuantityException e) {
            System.err.println("Selling error: " + e.getMessage());
        }

        // Statistic
        System.out.println("\nStore report:");
        System.out.println("Total revenue: " + store.getTotalRevenue());
        System.out.println("Salary expenses: " + store.getTotalSalaryExpenses());
        System.out.println("Profit: " + store.getProfit());
        System.out.println("Issued receipts: " + store.getReceiptCount());

        // Display all issued receipts
        System.out.println("\nAll issued receipts:");
        for (Receipt r : store.getReceipts()) {
            System.out.println("Receipt #" + r.getNumber() + " issued by " + r.getCashier().getName() + " on " + r.getDateTime());
            for (SaleItem item : r.getItems()) {
                System.out.println(" - " + item);
            }
            System.out.println("Total: " + r.getTotal());
            System.out.println("----------------------");
        }
    }
}