package com.airtribe.meditrack.entity;

public final class BillSummary {
    private final String billId;
    private final double total;

    public BillSummary(String billId, double total) {
        this.billId = billId; this.total = total;
    }
    public String getBillId() { return billId; }
    public double getTotal() { return total; }
}
