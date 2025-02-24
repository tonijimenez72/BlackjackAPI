package cat.itacademy.s05.t01.n01.blackjackapi.exception.custom;

import org.springframework.http.HttpStatus;

public class InvalidMoveException extends GameException {
    public InvalidMoveException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}