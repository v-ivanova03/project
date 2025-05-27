package org.example.market.service;

import org.example.market.data.CashRegister;
import org.example.market.data.Cashier;
import org.example.market.data.Product;
import org.example.market.data.Receipt;
import org.example.market.exception.InsufficientQuantityException;

import java.util.List;
import java.util.Map;

public interface StoreService {
    void addProduct(Product product);
    void addCashier(Cashier cashier);
    void addCashRegister(CashRegister register);
    Receipt sell(Map<String, Integer> shoppingList, CashRegister register) throws InsufficientQuantityException;
    double getTotalRevenue();
    double getTotalSalaryExpenses();
    double getProfit();
    int getReceiptCount();
    List<Receipt> getReceipts();
}
