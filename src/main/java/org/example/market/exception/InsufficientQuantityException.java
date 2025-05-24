package org.example.market.exception;

public class InsufficientQuantityException extends Exception {
    private final String productName;
    private final int requestedQuantity;
    private final int availableQuantity;

    public InsufficientQuantityException(String productName, int requestedQuantity, int availableQuantity) {
        super(("Insufficient quantity of: " + productName +
                " | Requested: " + requestedQuantity + ", Available: " + availableQuantity));
        this.productName = productName;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
