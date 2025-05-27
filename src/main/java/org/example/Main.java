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

        // Loading products
        Product bread = new FoodProduct("F10", "Whole Wheat Bread", 0.80, LocalDate.now().plusDays(4), 20);
        Product juice = new FoodProduct("F11", "Orange Juice", 1.50, LocalDate.now().plusDays(6), 15);
        Product shampoo = new NonFoodProduct("NF20", "Herbal Shampoo", 5.20, LocalDate.now().plusDays(365), 8);
        Product expiredMeat = new FoodProduct("F99", "Expired Sausage", 3.90, LocalDate.now().minusDays(2), 5);

        store.addProduct(bread);
        store.addProduct(juice);
        store.addProduct(shampoo);
        store.addProduct(expiredMeat);

        // Shopping
        Map<String, Integer> cart = new HashMap<>();
        cart.put("F10", 3); // Bread
        cart.put("F11", 2); // Juice
        cart.put("NF20", 1); // Shampoo
        cart.put("F99", 2); // Expired – will be ignored

        try {
            Receipt receipt = store.sell(cart, register);
            System.out.println("\nNote issued:");
            System.out.println(receipt);

            // Save to text file
            receiptService.saveToFile(receipt);

            // Reading from a text file
            System.out.println("\nText file content:");
            System.out.println(receiptService.readFromFile(receipt.getNumber()));

            // Serialization and deserialization
            receiptService.serializeReceipt(receipt);
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
    }
}