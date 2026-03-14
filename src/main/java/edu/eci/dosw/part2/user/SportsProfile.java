package edu.eci.dosw.part2.user;

public class SportsProfile extends Participant {

    private int jerseyNumber;

    public SportsProfile(String name, String email, String password, int jerseyNumber) {
        super(name, email, password);
        if (jerseyNumber < 1 || jerseyNumber > 99) {
            throw new IllegalArgumentException("Jersey number must be between 1 and 99");
        }
        this.jerseyNumber = jerseyNumber;
    }

    public int getJerseyNumber() { return jerseyNumber; }
    public void setJerseyNumber(int jerseyNumber) {
        if (jerseyNumber < 1 || jerseyNumber > 99) {
            throw new IllegalArgumentException("Jersey number must be between 1 and 99");
        }
        this.jerseyNumber = jerseyNumber;
    }
}
