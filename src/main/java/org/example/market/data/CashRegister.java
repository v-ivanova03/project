package org.example.market.data;

public class CashRegister {
    private String id;
    private Cashier cashier;

    public CashRegister(String id, Cashier cashier) {
        this.id = id;
        this.cashier = cashier;
    }

    public String getId() {
        return id;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    @Override
    public String toString() {
        return "CashRegister{" +
                "id='" + id + '\'' +
                ", cashier=" + cashier.getName() +
                '}';
    }
}
