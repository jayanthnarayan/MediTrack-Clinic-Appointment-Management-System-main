package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.InMemoryDataStore;
import com.airtribe.meditrack.util.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {
    private final InMemoryDataStore<Appointment> store = new InMemoryDataStore<>(Appointment.class);
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentService(PatientService ps, DoctorService ds) {
        this.patientService = ps; this.doctorService = ds;
    }

    public Appointment create(String doctorId, String patientId, LocalDateTime when) {
        String id = IdGenerator.getInstance().next("APT");
        Appointment a = new Appointment(id, doctorId, patientId, when);
        a.setStatus(AppointmentStatus.CONFIRMED);
        store.save(a);
        return a;
    }

    public List<Appointment> list() { return store.findAll(); }

    public void cancel(String apptId) {
        Appointment appt = store.findById(apptId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found: " + apptId));
        appt.setStatus(AppointmentStatus.CANCELLED);
        store.save(appt);
    }

    // Polymorphism via Payable (Doctor implements Payable)
    public Bill generateBill(String apptId) {
        Appointment appt = store.findById(apptId)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found: " + apptId));

        Doctor doctor = doctorService.list().stream()
                .filter(d -> d.getId().equals(appt.getDoctorId()))
                .findFirst().orElseThrow(() -> new RuntimeException("Doctor not found"));

        double base = doctor.baseAmount(); // dynamic dispatch if multiple Payable types
        String billId = IdGenerator.getInstance().next("BILL");
        return new Bill(billId, appt.getId(), base);
    }

    // CSV persistence (Bonus A)
    public void saveToCsv(String path) throws Exception {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"id","doctorId","patientId","when","status"});
        for (Appointment a : store.findAll()) {
            rows.add(new String[]{
                    a.getId(), a.getDoctorId(), a.getPatientId(),
                    DateUtil.formatDateTime(a.getWhen()),
                    a.getStatus().name()
            });
        }
        CSVUtil.write(path, rows);
    }

    public void loadFromCsv(String path) throws Exception {
        List<String[]> rows = CSVUtil.read(path);
        if (rows.isEmpty()) return;
        rows.remove(0); // header
        long max = 0;
        for (String[] r : rows) {
            if (r.length < 5) continue;
            String id = r[0];
            String did = r[1];
            String pid = r[2];
            LocalDateTime when = DateUtil.parseDateTime(r[3]);
            AppointmentStatus status = AppointmentStatus.valueOf(r[4]);
            Appointment a = new Appointment(id, did, pid, when);
            a.setStatus(status);
            store.save(a);
            try {
                long n = Long.parseLong(id.substring(id.indexOf('-') + 1));
                if (n > max) max = n;
            } catch (Exception ignore) {}
        }
        IdGenerator.getInstance().setFloor(max);
    }
}