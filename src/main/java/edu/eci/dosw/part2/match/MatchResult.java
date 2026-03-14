package edu.eci.dosw.part2.match;

public class MatchResult {

    private int homeGoals;
    private int awayGoals;

    public MatchResult(int homeGoals, int awayGoals) {
        if (homeGoals < 0 || awayGoals < 0) {
            throw new IllegalArgumentException("Goals cannot be negative");
        }
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public int getHomeGoals() { return homeGoals; }
    public int getAwayGoals() { return awayGoals; }

    public void setHomeGoals(int homeGoals) {
        if (homeGoals < 0) throw new IllegalArgumentException("Goals cannot be negative");
        this.homeGoals = homeGoals;
    }

    public void setAwayGoals(int awayGoals) {
        if (awayGoals < 0) throw new IllegalArgumentException("Goals cannot be negative");
        this.awayGoals = awayGoals;
    }
}
