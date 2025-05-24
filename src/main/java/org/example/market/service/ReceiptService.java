package org.example.market.service;

import org.example.market.data.Receipt;

public interface ReceiptService {
    void saveToFile(Receipt receipt);
    String readFromFile(int number);
    void serializeReceipt(Receipt receipt);
    Receipt deserializeReceipt(int number);
}
