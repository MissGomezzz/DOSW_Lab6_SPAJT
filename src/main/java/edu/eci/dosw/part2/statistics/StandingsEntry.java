package edu.eci.dosw.part2.statistics;

public class StandingsEntry {

    private int matchesPlayed;
    private int wins;
    private int draws;
    private int losses;
    private int goalsFor;
    private int goalsAgainst;
    private int points;

    public StandingsEntry() {}

    public void recordWin(int goalsFor, int goalsAgainst) {
        matchesPlayed++;
        wins++;
        points += 3;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
    }

    public void recordDraw(int goalsFor, int goalsAgainst) {
        matchesPlayed++;
        draws++;
        points += 1;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
    }

    public void recordLoss(int goalsFor, int goalsAgainst) {
        matchesPlayed++;
        losses++;
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
    }

    public int getGoalDifference() { return goalsFor - goalsAgainst; }

    public int getMatchesPlayed() { return matchesPlayed; }
    public int getWins() { return wins; }
    public int getDraws() { return draws; }
    public int getLosses() { return losses; }
    public int getGoalsFor() { return goalsFor; }
    public int getGoalsAgainst() { return goalsAgainst; }
    public int getPoints() { return points; }
}
