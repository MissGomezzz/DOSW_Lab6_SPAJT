package edu.eci.dosw.part2.security;

import java.util.Date;
import java.util.UUID;

public class AuditLog {

    private UUID id;
    private String action;
    private Date timestamp;

    public AuditLog(String action) {
        if (action == null || action.isBlank()) throw new IllegalArgumentException("Action cannot be blank");
        this.id = UUID.randomUUID();
        this.action = action;
        this.timestamp = new Date();
    }

    public UUID getId() { return id; }
    public String getAction() { return action; }
    public Date getTimestamp() { return timestamp; }
}
