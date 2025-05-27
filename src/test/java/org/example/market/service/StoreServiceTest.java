package org.example.market.service;

import org.example.market.data.*;
import org.example.market.data.Cashier;
import org.example.market.data.FoodProduct;
import org.example.market.exception.InsufficientQuantityException;
import org.example.market.service.impl.ReceiptServiceImpl;
import org.example.market.service.impl.StoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StoreServiceTest {
    private StoreService store;
    private CashRegister register;

    @BeforeEach
    public void setUp() {
        store = new StoreServiceImpl();
        Cashier cashier = new Cashier("C01", "Test Cashier", 1000);
        register = new CashRegister("R01", cashier);
        store.addCashier(cashier);
        store.addCashRegister(register);
    }

    @Test
    public void testSuccessfulSale() throws InsufficientQuantityException {
        Product cheese = new FoodProduct("F1", "Cheese", 3.0, LocalDate.now().plusDays(5), 10);
        store.addProduct(cheese);
        Map<String, Integer> cart = new HashMap<>();
        cart.put("F1", 2);

        Receipt receipt = store.sell(cart, register);

        assertNotNull(receipt);
        assertEquals(1, receipt.getItems().size());
        assertTrue(receipt.getTotal() > 0);
    }


    @Test
    public void testInsufficientQuantityException() {
        Product yogurt = new FoodProduct("F2", "Yogurt", 2.5, LocalDate.now().plusDays(3), 2);
        store.addProduct(yogurt);

        Map<String, Integer> cart = new HashMap<>();
        cart.put("F2", 5); // more than available

        assertThrows(InsufficientQuantityException.class, () -> {
            store.sell(cart, register);
        });
    }

    @Test
    public void testExpiredProductIsSkipped() throws InsufficientQuantityException {
        Product expired = new FoodProduct("F3", "Old Croissants", 2.0, LocalDate.now().minusDays(1), 5);
        store.addProduct(expired);

        Map<String, Integer> cart = new HashMap<>();
        cart.put("F3", 2);

        Receipt receipt = store.sell(cart, register);
        assertEquals(0, receipt.getItems().size());
        assertEquals(0.0, receipt.getTotal(), 0.001);
    }

    @Test
    void testAddCashRegister() {
        Cashier cashier = new Cashier("C02", "Anna Petrova", 1100);
        CashRegister reg = new CashRegister("R02", cashier);
        store.addCashier(cashier);
        store.addCashRegister(reg);

        assertDoesNotThrow(() -> {
            store.sell(new HashMap<>(), reg);
        });
    }
}
