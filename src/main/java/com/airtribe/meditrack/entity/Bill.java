package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;

public class Bill extends MedicalEntity {
    private String appointmentId;
    private double amountBeforeTax;
    private double tax;
    private double total;

    public Bill(String id, String appointmentId, double amountBeforeTax) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.amountBeforeTax = amountBeforeTax;
        this.tax = amountBeforeTax * Constants.TAX_RATE;
        this.total = amountBeforeTax + tax;
    }

    public String getAppointmentId() { return appointmentId; }
    public double getAmountBeforeTax() { return amountBeforeTax; }
    public double getTax() { return tax; }
    public double getTotal() { return total; }

    @Override public String toString() {
        return "Bill{id='" + id + "', appt='" + appointmentId + "', base=" + amountBeforeTax +
                ", tax=" + tax + ", total=" + total + "}";
    }
}