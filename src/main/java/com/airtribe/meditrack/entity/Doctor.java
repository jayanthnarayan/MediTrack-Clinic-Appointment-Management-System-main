package com.airtribe.meditrack.entity;


import com.airtribe.meditrack.interfaces.Payable;
import com.airtribe.meditrack.interfaces.Searchable;

public class Doctor extends Person implements Searchable, Payable {
    private Specialization specialization;
    private double consultationFee;

    public Doctor(String id, String name, int age, Specialization specialization, double consultationFee) {
        super(id, name, age);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
    }

    public Specialization getSpecialization() { return specialization; }
    public double getConsultationFee() { return consultationFee; }
    public void setConsultationFee(double fee) { this.consultationFee = fee; }

    @Override public boolean matches(String query) {
        String q = query.toLowerCase();
        return getName().toLowerCase().contains(q) || specialization.name().toLowerCase().contains(q);
    }
    @Override public double baseAmount() { return consultationFee; }

    @Override public String toString() {
        return super.toString() + " [" + specialization + ", fee=" + consultationFee + "]";
    }
}
