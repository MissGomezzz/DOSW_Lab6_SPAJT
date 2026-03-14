package edu.eci.dosw.part2.user;

import java.util.UUID;

public abstract class User {

    private UUID id;
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Invalid email");
        if (password == null || password.length() < 6) throw new IllegalArgumentException("Password too short");
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}
