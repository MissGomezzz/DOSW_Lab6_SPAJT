package edu.eci;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import edu.eci.dosw.part2.*;

import java.util.Date;
import java.util.List;

class TournamentSimulationTest {
    private TournamentSimulation tournament;

    @BeforeEach
    void setUp() {
        tournament = new TournamentSimulation();
    }

    @Test
    @DisplayName("1.1 - Should register organizer, referee and captain successfully")
    void shouldRegisterAllRoles() {
        tournament.registerOrganizer("Alice",  "alice@email.com",  "alicepass");
        tournament.registerReferee("Bob",    "bob@email.com",    "bobpass12");
        tournament.registerCaptain("Carlos", "carlos@email.com", "carlospass");

        List<String> logs = tournament.getAuditLogs()
                .stream().map(l -> l.getAction()).toList();

        assertTrue(logs.stream().anyMatch(l -> l.contains("ORGANIZER_REGISTERED")));
        assertTrue(logs.stream().anyMatch(l -> l.contains("REFEREE_REGISTERED")));
        assertTrue(logs.stream().anyMatch(l -> l.contains("CAPTAIN_REGISTERED")));
        assertEquals(3, tournament.getAuditLogs().size());
    }

    @Test
    void shouldRejectOrganizerWithInvalidEmail() {
        assertThrows(IllegalArgumentException.class,
                () -> tournament.registerOrganizer("Alice", "not-an-email", "alicepass"));
    }

    @Test
    void shouldRejectOrganizerWithShortPassword() {
        assertThrows(IllegalArgumentException.class,
                () -> tournament.registerOrganizer("Alice", "alice@email.com", "123"));
    }

    @Test
    void shouldCreateTeams() {
        tournament.registerCaptain("Carlos", "carlos@email.com", "carlospass");

        tournament.createTeam("Eagles FC");
        tournament.createTeam("Tigers SC");

        List<String> logs = tournament.getAuditLogs()
                .stream().map(l -> l.getAction()).toList();

        assertTrue(logs.stream().anyMatch(l -> l.contains("TEAM_CREATED: Eagles FC")));
        assertTrue(logs.stream().anyMatch(l -> l.contains("TEAM_CREATED: Tigers SC")));
    }

    @Test
    void shouldFailCreateTeamWithoutCaptain() {
        assertThrows(IllegalStateException.class,
                () -> tournament.createTeam("Eagles FC"));
    }

    @Test
    void shouldRejectPlayerWithBadJersey() {
        tournament.registerCaptain("Carlos", "carlos@email.com", "carlospass");
        tournament.createTeam("Eagles FC");

        assertThrows(IllegalArgumentException.class,
                () -> tournament.addPlayerToTeam("Eagles FC", "Rogue", "rogue@email.com", "roguepass", 0));
    }

    @Test
    void shouldFailAddPlayerToUnknownTeam() {
        tournament.registerCaptain("Carlos", "carlos@email.com", "carlospass");

        assertThrows(IllegalArgumentException.class,
                () -> tournament.addPlayerToTeam("Ghost FC", "Diego", "diego@email.com", "diegopass", 9));
    }

}
