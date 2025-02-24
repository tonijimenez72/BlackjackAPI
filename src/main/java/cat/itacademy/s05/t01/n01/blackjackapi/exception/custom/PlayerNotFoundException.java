package cat.itacademy.s05.t01.n01.blackjackapi.exception.custom;

import org.springframework.http.HttpStatus;

public class PlayerNotFoundException extends GameException {
    public PlayerNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}