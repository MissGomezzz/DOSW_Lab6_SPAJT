package edu.eci.dosw.part2.statistics;

public class PlayerStatistics {

    private int goals;
    private int yellowCards;
    private int redCards;

    public PlayerStatistics() {
        this.goals = 0;
        this.yellowCards = 0;
        this.redCards = 0;
    }

    public void addGoal() { goals++; }
    public void addYellowCard() { yellowCards++; }
    public void addRedCard() { redCards++; }

    public int getGoals() { return goals; }
    public int getYellowCards() { return yellowCards; }
    public int getRedCards() { return redCards; }
}
