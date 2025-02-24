package cat.itacademy.s05.t01.n01.blackjackapi.exception.custom;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends GameException {
    public InvalidRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}