package cat.itacademy.s05.t01.n01.blackjackapi.exception.custom;

import org.springframework.http.HttpStatus;

public class PlayerCreationException extends GameException {
    public PlayerCreationException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}