package edu.eci.dosw.part2.match;

import java.util.Date;
import java.util.UUID;

public class Match {

    private UUID id;
    private Date date;
    private MatchStatus status;
    private MatchResult result;

    public Match(Date date) {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");
        this.id = UUID.randomUUID();
        this.date = date;
        this.status = MatchStatus.SCHEDULED;
    }

    public void setResult(MatchResult result) {
        if (result == null) throw new IllegalArgumentException("Result cannot be null");
        this.result = result;
        this.status = MatchStatus.PLAYED;
    }

    public UUID getId() { return id; }
    public Date getDate() { return date; }
    public MatchStatus getStatus() { return status; }
    public MatchResult getResult() { return result; }
}
