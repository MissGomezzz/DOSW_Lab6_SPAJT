package edu.eci.dosw.part2.user;

public class Participant extends User {

    private boolean availableForTeam;

    public Participant(String name, String email, String password) {
        super(name, email, password);
        this.availableForTeam = true;
    }

    public void setAvailability(boolean status) {
        this.availableForTeam = status;
    }

    public boolean isAvailableForTeam() { return availableForTeam; }
}
