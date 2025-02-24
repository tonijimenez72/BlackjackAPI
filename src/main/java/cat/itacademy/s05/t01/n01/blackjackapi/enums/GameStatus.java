package cat.itacademy.s05.t01.n01.blackjackapi.enums;

public enum GameStatus {
    IN_PROGRESS("In Progress"),
    FINISHED("Finished");

    private final String displayName;

    GameStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}