package edu.eci.dosw.part2.user;

import java.util.ArrayList;
import java.util.List;

public class Captain extends Participant {

    private List<String> teams = new ArrayList<>();

    public Captain(String name, String email, String password) {
        super(name, email, password);
    }

    public String createTeam(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Team name cannot be blank");
        teams.add(name);
        return name;
    }

    public List<String> getTeams() { return List.copyOf(teams); }
}
