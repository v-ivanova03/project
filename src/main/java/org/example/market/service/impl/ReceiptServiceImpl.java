package org.example.market.service.impl;

import org.example.market.data.Receipt;
import org.example.market.service.ReceiptService;

import java.io.*;

public class ReceiptServiceImpl implements ReceiptService {
    private static final String TXT_FOLDER = "receipts/";
    private static final String SER_FOLDER = "receipts/";

    @Override
    public void saveToFile(Receipt receipt) {
        String filename = TXT_FOLDER + "receipt_" + receipt.getNumber() + ".txt";
        try(FileWriter writer = new FileWriter(filename)) {
            writer.write(receipt.toString());
        }catch (IOException e){
            System.err.println("Error saving receipt: " + filename);
        }
    }

    @Override
    public String readFromFile(int number) {
        String filename = TXT_FOLDER + "receipt_" + number + ".txt";
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading receipt: " + filename);
        }
        return sb.toString();
    }

    @Override
    public void serializeReceipt(Receipt receipt) {
        String fileName = SER_FOLDER + "receipt_" + receipt.getNumber() + ".dat";
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(receipt);
        }catch (IOException e){
            System.err.println("Error serialization receipt: " + fileName);
        }
    }

    @Override
    public Receipt deserializeReceipt(int number) {
        String fileName = SER_FOLDER + "receipt_" + number + ".dat";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Receipt) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Грешка при десериализация: " + fileName);
            return null;
        }
    }
}