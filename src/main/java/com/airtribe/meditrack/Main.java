package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final PatientService patientService = new PatientService();
    private static final DoctorService doctorService = new DoctorService();
    private static final AppointmentService appointmentService =
            new AppointmentService(patientService, doctorService);

    public static void main(String[] args) {
        System.out.println("=== MediTrack ===");

        // Bonus A: load persisted CSV data if flag present
        boolean load = false;
        for (String a : args) if ("--loadData".equalsIgnoreCase(a)) load = true;
        if (load) {
            try {
                doctorService.loadFromCsv(Constants.DOCTOR_CSV);
                patientService.loadFromCsv(Constants.PATIENT_CSV);
                appointmentService.loadFromCsv(Constants.APPOINTMENT_CSV);
                System.out.println("Data loaded from CSV.");
            } catch (Exception e) {
                System.out.println("CSV load failed: " + e.getMessage());
            }
        }

        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. List Patients");
            System.out.println("4. List Doctors");
            System.out.println("5. Search Patient (1)By ID (2)By Name (3)By Age");
            System.out.println("6. Create Appointment");
            System.out.println("7. List Appointments");
            System.out.println("8. Cancel Appointment");
            System.out.println("9. Generate Bill");
            System.out.println("10. Save CSV (Doctors/Patients/Appointments)");
            System.out.println("11. Load CSV");
            System.out.println("12. Doctor Analytics (Streams)");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = safeInt(sc);
            try {
                switch (choice) {
                    case 1 -> addPatient(sc);
                    case 2 -> addDoctor(sc);
                    case 3 -> patientService.list().forEach(System.out::println);
                    case 4 -> doctorService.list().forEach(System.out::println);
                    case 5 -> searchPatientMenu(sc);
                    case 6 -> createAppointment(sc);
                    case 7 -> appointmentService.list().forEach(System.out::println);
                    case 8 -> cancelAppointment(sc);
                    case 9 -> generateBill(sc);
                    case 10 -> saveCsv();
                    case 11 -> loadCsv();
                    case 12 -> doctorAnalytics(sc);
                    case 0 -> run = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        System.out.println("Exiting MediTrack.");
    }

    private static int safeInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static void addPatient(Scanner sc) {
        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        int age = safeInt(sc); sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();

        ContactInfo ci = new ContactInfo(phone, email);
        var p = patientService.createPatient(name, age, ci);
        System.out.println("Added Patient: " + p);
    }

    private static void addDoctor(Scanner sc) {
        sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        int age = safeInt(sc); sc.nextLine();
        System.out.print("Specialization (GENERAL, CARDIOLOGY, ORTHOPEDICS, DERMATOLOGY): ");
        String spec = sc.nextLine().trim().toUpperCase();
        System.out.print("Consultation Fee: ");
        double fee = sc.nextDouble();

        var d = doctorService.createDoctor(name, age, Specialization.valueOf(spec), fee);
        System.out.println("Added Doctor: " + d);
    }

    private static void searchPatientMenu(Scanner sc) {
        System.out.print("Choose: ");
        int ch = safeInt(sc); sc.nextLine();
        switch (ch) {
            case 1 -> {
                System.out.print("ID: "); String id = sc.nextLine();
                System.out.println(patientService.searchPatient(id).orElse(null));
            }
            case 2 -> {
                System.out.print("Name: "); String name = sc.nextLine();
                patientService.searchPatientByName(name).forEach(System.out::println);
            }
            case 3 -> {
                System.out.print("Age: "); int age = safeInt(sc);
                patientService.searchPatient(age).forEach(System.out::println);
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void createAppointment(Scanner sc) {
        sc.nextLine();
        System.out.print("Doctor ID: "); String did = sc.nextLine();
        System.out.print("Patient ID: "); String pid = sc.nextLine();
        System.out.print("DateTime (yyyy-MM-dd HH:mm): "); String dt = sc.nextLine();
        LocalDateTime when = DateUtil.parseDateTime(dt);

        var appt = appointmentService.create(did, pid, when);
        System.out.println("Created Appointment: " + appt);
    }

    private static void cancelAppointment(Scanner sc) {
        sc.nextLine();
        System.out.print("Appointment ID: "); String aid = sc.nextLine();
        appointmentService.cancel(aid);
        System.out.println("Cancelled.");
    }

    private static void generateBill(Scanner sc) {
        sc.nextLine();
        System.out.print("Appointment ID: "); String aid = sc.nextLine();
        Bill bill = appointmentService.generateBill(aid);
        System.out.println("Bill: " + bill);
        BillSummary summary = new BillSummary(bill.getId(), bill.getTotal());
        System.out.println("Bill Summary (immutable): " + summary.getBillId() + " -> " + summary.getTotal());
    }

    private static void saveCsv() {
        try {
            doctorService.saveToCsv(Constants.DOCTOR_CSV);
            patientService.saveToCsv(Constants.PATIENT_CSV);
            appointmentService.saveToCsv(Constants.APPOINTMENT_CSV);
            System.out.println("Saved CSV to data/.");
        } catch (Exception e) {
            System.out.println("CSV save failed: " + e.getMessage());
        }
    }

    private static void loadCsv() {
        try {
            doctorService.loadFromCsv(Constants.DOCTOR_CSV);
            patientService.loadFromCsv(Constants.PATIENT_CSV);
            appointmentService.loadFromCsv(Constants.APPOINTMENT_CSV);
            System.out.println("Loaded CSV from data/.");
        } catch (Exception e) {
            System.out.println("CSV load failed: " + e.getMessage());
        }
    }

    private static void doctorAnalytics(Scanner sc) {
        System.out.println("1) Filter by specialization  2) Average consultation fee");
        System.out.print("Choose: ");
        int ch = safeInt(sc); sc.nextLine();
        switch (ch) {
            case 1 -> {
                System.out.print("Specialization: ");
                String s = sc.nextLine().trim().toUpperCase();
                List<Doctor> list = doctorService.filterBySpecialization(Specialization.valueOf(s));
                list.forEach(System.out::println);
            }
            case 2 -> System.out.println("Average Fee: " + doctorService.averageFee());
            default -> {}
        }
    }
}