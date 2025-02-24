package cat.itacademy.s05.t01.n01.blackjackapi.exception.custom;

import org.springframework.http.HttpStatus;

public class GameAlreadyEndedException extends GameException {
    public GameAlreadyEndedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}