package edu.eci.dosw.part2;

import edu.eci.dosw.part2.match.*;
import edu.eci.dosw.part2.user.*;
import edu.eci.dosw.part2.security.*;
import edu.eci.dosw.part2.statistics.*;

import java.util.*;

public class TournamentSimulation {
    private final List<AuditLog>         auditLogs        = new ArrayList<>();
    private final List<Match>            matches          = new ArrayList<>();
    private final Map<String, List<SportsProfile>> teams = new LinkedHashMap<>();
    private final Map<String, StandingsEntry>  standings  = new LinkedHashMap<>();
    private final Map<UUID, PlayerStatistics>  playerStats = new LinkedHashMap<>();

    private Organizer  organizer;
    private Referee    referee;
    private Captain    captain;


    public void registerOrganizer(String name, String email, String password) {
        this.organizer = new Organizer(name, email, password);
        audit("ORGANIZER_REGISTERED: " + name);
    }

    public void registerReferee(String name, String email, String password) {
        this.referee = new Referee(name, email, password);
        audit("REFEREE_REGISTERED: " + name);
    }

    public void registerCaptain(String name, String email, String password) {
        this.captain = new Captain(name, email, password);
        audit("CAPTAIN_REGISTERED: " + name);
    }

    public String createTeam(String teamName) {
        requireCaptain();
        String created = captain.createTeam(teamName);
        teams.put(created, new ArrayList<>());
        standings.put(created, new StandingsEntry());
        audit("TEAM_CREATED: " + teamName);
        return created;
    }

    public SportsProfile addPlayerToTeam(String teamName, String playerName,
                                         String email, String password, int jerseyNumber) {
        requireTeam(teamName);
        SportsProfile player = new SportsProfile(playerName, email, password, jerseyNumber);
        teams.get(teamName).add(player);
        playerStats.put(player.getId(), new PlayerStatistics());
        audit("PLAYER_ADDED: " + playerName + " -> " + teamName);
        return player;
    }

    public void setPlayerAvailability(SportsProfile player, boolean available) {
        player.setAvailability(available);
        audit("AVAILABILITY_CHANGED: " + player.getName() + " -> " + available);
    }


    public Match scheduleMatch(Date date) {
        requireOrganizer();
        Match match = new Match(date);
        matches.add(match);
        audit("MATCH_SCHEDULED: " + match.getId());
        return match;
    }

    public void playMatch(Match match, String homeTeam, String awayTeam,
                          int homeGoals, int awayGoals) {
        requireReferee();
        requireTeam(homeTeam);
        requireTeam(awayTeam);

        MatchResult result = new MatchResult(homeGoals, awayGoals);
        match.setResult(result);

        updateStandings(homeTeam, awayTeam, homeGoals, awayGoals);
        audit("MATCH_PLAYED: " + homeTeam + " " + homeGoals + "-" + awayGoals + " " + awayTeam);
    }

    public Card issueCard(Match match, SportsProfile player, int minute, CardType type) {
        requireReferee();
        Card card = new Card(minute, type);

        PlayerStatistics stats = playerStats.get(player.getId());
        if (stats != null) {
            if (type == CardType.YELLOW) stats.addYellowCard();
            else                          stats.addRedCard();
        }

        audit("CARD_ISSUED: " + type + " -> " + player.getName() + " @ " + minute + "'");
        return card;
    }

    public Goal recordGoal(Match match, SportsProfile player, int minute) {
        Goal goal = new Goal(minute);

        PlayerStatistics stats = playerStats.get(player.getId());
        if (stats != null) stats.addGoal();

        audit("GOAL_SCORED: " + player.getName() + " @ " + minute + "'");
        return goal;
    }

    public List<SportsProfile> getTeamPlayers(String teamName) {
        requireTeam(teamName);
        return Collections.unmodifiableList(teams.get(teamName));
    }

    public StandingsEntry getStandings(String teamName) {
        requireTeam(teamName);
        return standings.get(teamName);
    }

    public PlayerStatistics getPlayerStats(SportsProfile player) {
        return playerStats.get(player.getId());
    }

    public List<Match> getMatches() {
        return Collections.unmodifiableList(matches);
    }

    public List<AuditLog> getAuditLogs() {
        return Collections.unmodifiableList(auditLogs);
    }

    public List<Match> getMatchesByStatus(MatchStatus status) {
        List<Match> result = new ArrayList<>();
        for (Match m : matches) {
            if (m.getStatus() == status) result.add(m);
        }
        return result;
    }

    public Map<String, StandingsEntry> getFullStandings() {
        return Collections.unmodifiableMap(standings);
    }

    public String getLeader() {
        return standings.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().getPoints()))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private void updateStandings(String home, String away, int homeGoals, int awayGoals) {
        StandingsEntry homeEntry = standings.get(home);
        StandingsEntry awayEntry = standings.get(away);

        if (homeGoals > awayGoals) {
            homeEntry.recordWin(homeGoals, awayGoals);
            awayEntry.recordLoss(awayGoals, homeGoals);
        } else if (homeGoals < awayGoals) {
            homeEntry.recordLoss(homeGoals, awayGoals);
            awayEntry.recordWin(awayGoals, homeGoals);
        } else {
            homeEntry.recordDraw(homeGoals, awayGoals);
            awayEntry.recordDraw(awayGoals, homeGoals);
        }
    }

    private void audit(String action) {
        auditLogs.add(new AuditLog(action));
    }

    private void requireOrganizer() {
        if (organizer == null) throw new IllegalStateException("No organizer registered");
    }

    private void requireReferee() {
        if (referee == null) throw new IllegalStateException("No referee registered");
    }

    private void requireCaptain() {
        if (captain == null) throw new IllegalStateException("No captain registered");
    }

    private void requireTeam(String teamName) {
        if (!teams.containsKey(teamName))
            throw new IllegalArgumentException("Team not found: " + teamName);
    }
}
