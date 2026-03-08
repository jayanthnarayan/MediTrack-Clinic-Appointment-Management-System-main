package com.airtribe.meditrack.entity;

public class ContactInfo implements Cloneable {
    private String phone;
    private String email;

    public ContactInfo(String phone, String email) { this.phone = phone; this.email = email; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }

    @Override public com.airtribe.meditrack.entity.ContactInfo clone() {
        return new com.airtribe.meditrack.entity.ContactInfo(phone, email);
    }

    @Override public String toString() {
        return "ContactInfo{phone='" + phone + "', email='" + email + "'}";
    }
}