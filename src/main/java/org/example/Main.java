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
        Cashier cashier = new Cashier("C01", "Maria Ivanova", 1200);
        store.addCashier(cashier);

        // Loading products
        Product milk = new FoodProduct("P01", "Milk", 1.2, LocalDate.now().plusDays(5), 10);
        Product detergent = new NonFoodProduct("P02", "Detergent", 3.5, LocalDate.now().plusDays(2), 5);
        Product expired = new FoodProduct("P03", "Croissant", 1.0, LocalDate.now().minusDays(1), 4);

        store.addProduct(milk);
        store.addProduct(detergent);
        store.addProduct(expired);

        // Shopping
        Map<String, Integer> cart = new HashMap<>();
        cart.put("P01", 2); // Milk
        cart.put("P02", 1); // Detergent
        cart.put("P03", 1); // Expired product (will be skipped)

        try {
            Receipt receipt = store.sell(cart, cashier);
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
