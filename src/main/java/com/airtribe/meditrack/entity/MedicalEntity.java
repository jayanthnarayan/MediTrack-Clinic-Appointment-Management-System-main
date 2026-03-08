package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;

public class MedicalEntity {
    protected String id;
    protected LocalDateTime createdAt = LocalDateTime.now();
    public String getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
