package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.util.CSVUtil;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.InMemoryDataStore;
import com.airtribe.meditrack.util.Validator;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {
    private final InMemoryDataStore<Doctor> store = new InMemoryDataStore<>(Doctor.class);

    public Doctor createDoctor(String name, int age, Specialization spec, double fee) {
        Validator.notBlank(name, "name");
        Validator.positive(age, "age");
        Validator.nonNegative(fee, "fee");

        String id = IdGenerator.getInstance().next("DOC");
        Doctor d = new Doctor(id, name, age, spec, fee);
        store.save(d);
        return d;
    }

    public List<Doctor> list() { return store.findAll(); }

    // Streams + Lambdas (Bonus D)
    public List<Doctor> filterBySpecialization(Specialization spec) {
        return store.findAll().stream().filter(d -> d.getSpecialization() == spec).collect(Collectors.toList());
    }
    public double averageFee() {
        DoubleSummaryStatistics stats = store.findAll().stream().mapToDouble(Doctor::getConsultationFee).summaryStatistics();
        return stats.getAverage();
    }

    // CSV persistence (Bonus A)
    public void saveToCsv(String path) throws Exception {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"id","name","age","specialization","fee"});
        for (Doctor d : store.findAll()) {
            rows.add(new String[]{
                    d.getId(), d.getName(), String.valueOf(d.getAge()),
                    d.getSpecialization().name(), String.valueOf(d.getConsultationFee())
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
            String name = r[1];
            int age = r[2].isBlank() ? 0 : Integer.parseInt(r[2]);
            Specialization spec = r[3].isBlank() ? Specialization.GENERAL : Specialization.valueOf(r[3]);
            double fee = r[4].isBlank() ? 0.0 : Double.parseDouble(r[4]);
            Doctor d = new Doctor(id, name, age, spec, fee);
            store.save(d);
            try {
                long n = Long.parseLong(id.substring(id.indexOf('-') + 1));
                if (n > max) max = n;
            } catch (Exception ignore) {}
        }
        IdGenerator.getInstance().setFloor(max);
    }
}