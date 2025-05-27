package org.example.market.service;

import org.example.market.data.*;
import org.example.market.service.impl.ReceiptServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ReceiptServiceTest {
    private ReceiptService receiptService;
    private Receipt sampleReceipt;

    @BeforeEach
    public void setUp() {
        receiptService = new ReceiptServiceImpl();

        Product milk = new FoodProduct("P1", "Milk", 1.2, LocalDate.now().plusDays(5), 10);
        Product detergent = new NonFoodProduct("P2", "Detergent", 2.5, LocalDate.now().plusDays(30), 5);

        SaleItem item1 = new SaleItem(milk, 2, milk.getSellingPrice(LocalDate.now(), 3, 0.3));
        SaleItem item2 = new SaleItem(detergent, 1, detergent.getSellingPrice(LocalDate.now(), 3, 0.3));

        Cashier cashier = new Cashier("C01", "Maria Ivanova", 1200);
        sampleReceipt = new Receipt(cashier, Arrays.asList(item1, item2));
    }

    @Test
    public void testSaveToFile() {
        receiptService.saveToFile(sampleReceipt);
        File file = new File("receipts/receipt_" + sampleReceipt.getNumber() + ".txt");
        assertTrue(file.exists(), "Text file should be created");
    }

    @Test
    public void testReadFromFile() {
        receiptService.saveToFile(sampleReceipt);
        String content = receiptService.readFromFile(sampleReceipt.getNumber());
        assertNotNull(content);
        assertTrue(content.contains("Receipt #" + sampleReceipt.getNumber()));
        assertTrue(content.contains(sampleReceipt.getCashier().getName()));
    }

    @Test
    public void testSerializeReceipt() {
        receiptService.serializeReceipt(sampleReceipt);
        File file = new File("receipts/receipt_" + sampleReceipt.getNumber() + ".dat");
        assertTrue(file.exists(), "Serialized file should be created");
    }

    @Test
    public void testDeserializeReceipt() {
        receiptService.serializeReceipt(sampleReceipt);
        Receipt loaded = receiptService.deserializeReceipt(sampleReceipt.getNumber());
        assertNotNull(loaded);
        assertEquals(sampleReceipt.getNumber(), loaded.getNumber());
        assertEquals(sampleReceipt.getTotal(), loaded.getTotal(), 0.01);
        assertEquals(sampleReceipt.getCashier().getName(), loaded.getCashier().getName());
    }
}