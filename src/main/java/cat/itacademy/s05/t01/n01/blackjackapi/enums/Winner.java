package cat.itacademy.s05.t01.n01.blackjackapi.enums;

public enum Winner {
    NONE("None"),
    PLAYER("Player"),
    CROUPIER("Croupier"),
    TIE("Tie");

    private final String displayWinner;

    Winner(String displayWinner) {
        this.displayWinner = displayWinner;
    }

    @Override
    public String toString() {
        return displayWinner;
    }
}