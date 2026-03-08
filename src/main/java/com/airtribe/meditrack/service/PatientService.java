package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.ContactInfo;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.InMemoryDataStore;
import com.airtribe.meditrack.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientService {
    private final InMemoryDataStore<Patient> store = new InMemoryDataStore<>(Patient.class);

    public Patient createPatient(String name, int age, ContactInfo contact) {
        Validator.notBlank(name, "name");
        Validator.positive(age, "age");
        if (contact == null) throw new InvalidDataException("contact info required");

        String id = IdGenerator.getInstance().next("PAT");
        String pnum = "P-" + id.substring(id.indexOf('-') + 1);
        Patient p = new Patient(id, name, age, pnum, contact);
        store.save(p);
        return p;
    }

    public List<Patient> list() { return store.findAll(); }

    // Overloading
    public Optional<Patient> searchPatient(String id) { return store.findById(id); }
    public List<Patient> searchPatient(int age) {
        return store.findAll().stream().filter(p -> p.getAge() == age).collect(Collectors.toList());
    }
    public List<Patient> searchPatientByName(String name) {
        String q = name.toLowerCase();
        return store.findAll().stream().filter(p -> p.getName().toLowerCase().contains(q)).collect(Collectors.toList());
    }

    public void delete(String id) { store.deleteById(id); }

    // CSV persistence (Bonus A)
    public void saveToCsv(String path) throws Exception {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"id","name","age","patientNumber","phone","email"});
        for (Patient p : store.findAll()) {
            rows.add(new String[]{
                    p.getId(), p.getName(), String.valueOf(p.getAge()),
                    p.getPatientNumber(),
                    p.getContactInfo() == null ? "" : p.getContactInfo().getPhone(),
                    p.getContactInfo() == null ? "" : p.getContactInfo().getEmail()
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
            if (r.length < 6) continue;
            String id = r[0];
            String name = r[1];
            int age = r[2].isBlank() ? 0 : Integer.parseInt(r[2]);
            String pnum = r[3];
            String phone = r[4];
            String email = r[5];
            Patient p = new Patient(id, name, age, pnum, new ContactInfo(phone, email));
            store.save(p);
            // adjust generator floor to avoid id collision
            try {
                long n = Long.parseLong(id.substring(id.indexOf('-') + 1));
                if (n > max) max = n;
            } catch (Exception ignore) {}
        }
        IdGenerator.getInstance().setFloor(max);
    }
}