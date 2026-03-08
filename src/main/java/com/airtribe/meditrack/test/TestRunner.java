package com.airtribe.meditrack.test;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.*;

import java.time.LocalDateTime;

public class TestRunner {
    public static void main(String[] args) {
        var ps = new PatientService();
        var ds = new DoctorService();
        var as = new AppointmentService(ps, ds);

        var p = ps.createPatient("Alice", 30, new ContactInfo("9999999999","a@x.com"));
        var d = ds.createDoctor("Dr Bob", 45, Specialization.GENERAL, 500.0);

        var a = as.create(d.getId(), p.getId(), LocalDateTime.now().plusDays(1));
        var bill = as.generateBill(a.getId());
        System.out.println(p);
        System.out.println(d);
        System.out.println(a);
        System.out.println(bill);

        // Deep copy demo
        var p2 = p.clone();
        p2.getContactInfo().setPhone("0000000000");
        System.out.println("Original after clone edit: " + p);  // unchanged → deep copy works
        System.out.println("Clone: " + p2);
    }
}