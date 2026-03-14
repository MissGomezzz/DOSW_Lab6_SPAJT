package edu.eci.dosw.part2.match;

public class Goal {

    private int minute;

    public Goal(int minute) {
        if (minute < 1) throw new IllegalArgumentException("Minute must be positive");
        this.minute = minute;
    }

    public int getMinute() { return minute; }
}
