package edu.eci.dosw.part2.match;

public class Card {

    private int minute;
    private CardType type;

    public Card(int minute, CardType type) {
        if (minute < 1) throw new IllegalArgumentException("Minute must be positive");
        if (type == null) throw new IllegalArgumentException("Card type cannot be null");
        this.minute = minute;
        this.type = type;
    }

    public int getMinute() { return minute; }
    public CardType getType() { return type; }
}
