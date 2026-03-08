package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.Searchable;

public class Patient extends Person implements Cloneable, Searchable {
    private String patientNumber;
    private ContactInfo contactInfo;

    public Patient(String id, String name, int age, String patientNumber, ContactInfo contactInfo) {
        super(id, name, age);
        this.patientNumber = patientNumber;
        this.contactInfo = contactInfo;
    }

    public String getPatientNumber() { return patientNumber; }
    public ContactInfo getContactInfo() { return contactInfo; }
    public void setContactInfo(ContactInfo ci) { this.contactInfo = ci; }

    @Override public boolean matches(String query) {
        String q = query.toLowerCase();
        return getName().toLowerCase().contains(q)
                || patientNumber.toLowerCase().contains(q)
                || (contactInfo != null && (
                (contactInfo.getPhone() != null && contactInfo.getPhone().toLowerCase().contains(q)) ||
                        (contactInfo.getEmail() != null && contactInfo.getEmail().toLowerCase().contains(q))
        ));
    }

    @Override public Patient clone() {
        return new Patient(this.id, this.getName(), this.getAge(), this.patientNumber,
                this.contactInfo == null ? null : this.contactInfo.clone());
    }

    @Override public String toString() {
        return super.toString() + " [patientNumber=" + patientNumber + ", " + contactInfo + "]";
    }
}