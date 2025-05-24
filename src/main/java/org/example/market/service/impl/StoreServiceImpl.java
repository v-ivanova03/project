package org.example.market.service.impl;

import org.example.market.data.Cashier;
import org.example.market.data.Product;
import org.example.market.data.Receipt;
import org.example.market.data.SaleItem;
import org.example.market.exception.InsufficientQuantityException;
import org.example.market.service.StoreService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreServiceImpl implements StoreService {
    private final List<Product> stock = new ArrayList<>();
    private final List<Receipt> receipts = new ArrayList<>();
    private final List<Cashier> cashiers = new ArrayList<>();
    private double deliveryCost = 0;

    private final int discountThreshold = 3;
    private final double discountPercentage = 0.3;

    @Override
    public void addProduct(Product product) {
        stock.add(product);
        deliveryCost += product.getDeliveryPrice() * product.getQuantity();
    }

    @Override
    public void addCashier(Cashier cashier) {
        cashiers.add(cashier);
    }

    @Override
    public Receipt sell(Map<String, Integer> shoppingList, Cashier cashier) throws InsufficientQuantityException{
       List<SaleItem> saleItems = new ArrayList<>();
       LocalDate today = LocalDate.now();
       for (Map.Entry<String, Integer> entry : shoppingList.entrySet()) {
           Product product = stock.stream()
                   .filter(p-> p.getId().equals(entry.getKey()))
                   .findFirst()
                   .orElseThrow(()-> new RuntimeException("Product with ID " + entry.getKey() + " not found"));
           if(product.isExpired(today)) continue;
           int requested = entry.getValue();
           if(product.getQuantity() < requested) {
               throw new InsufficientQuantityException(product.getName(), requested, product.getQuantity());
           }
           double unitPrice = product.getSellingPrice(today, discountThreshold, discountPercentage);
           saleItems.add(new SaleItem(product, requested, unitPrice));
           product.setQuantity(product.getQuantity() - requested);
       }
       Receipt receipt = new Receipt(cashier, saleItems);
       receipts.add(receipt);
       return receipt;
    }

    @Override
    public double getTotalRevenue() {
        return receipts.stream().mapToDouble(Receipt::getTotal).sum();
    }

    @Override
    public double getTotalSalaryExpenses(){
        return cashiers.stream().mapToDouble(Cashier::getSalary).sum();
    }

    @Override
    public double getProfit(){
        return getTotalRevenue() - deliveryCost - getTotalSalaryExpenses();
    }

    @Override
    public int getReceiptCount() {
        return receipts.size();
    }

    @Override
    public List<Receipt> getReceipts() {
        return receipts;
    }
}
