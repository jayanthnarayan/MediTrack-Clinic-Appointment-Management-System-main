package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Appointment extends MedicalEntity implements Cloneable {
    private String doctorId;
    private String patientId;
    private LocalDateTime when;
    private AppointmentStatus status = AppointmentStatus.PENDING;
    private Map<String, String> attributes = new HashMap<>();

    public Appointment(String id, String doctorId, String patientId, LocalDateTime when) {
        this.id = id; this.doctorId = doctorId; this.patientId = patientId; this.when = when;
    }

    public String getDoctorId() { return doctorId; }
    public String getPatientId() { return patientId; }
    public LocalDateTime getWhen() { return when; }
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    public Map<String, String> getAttributes() { return attributes; }

    @Override public Appointment clone() {
        Appointment copy = new Appointment(this.id, this.doctorId, this.patientId, this.when);
        copy.status = this.status;
        copy.attributes = new HashMap<>(this.attributes);
        return copy;
    }

    @Override public String toString() {
        return "Appointment{id='" + id + "', doctorId='" + doctorId + "', patientId='" + patientId +
                "', when=" + when + ", status=" + status + "}";
    }
}