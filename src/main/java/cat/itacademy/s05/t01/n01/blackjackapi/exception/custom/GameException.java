package cat.itacademy.s05.t01.n01.blackjackapi.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class GameException extends RuntimeException {
    private final HttpStatus status;

    public GameException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
