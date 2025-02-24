package cat.itacademy.s05.t01.n01.blackjackapi.exception.custom;

import org.springframework.http.HttpStatus;

public class InvalidGameIdException extends GameException{
    public InvalidGameIdException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}