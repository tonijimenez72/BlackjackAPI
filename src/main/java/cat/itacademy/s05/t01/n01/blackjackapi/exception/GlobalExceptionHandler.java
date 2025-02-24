package cat.itacademy.s05.t01.n01.blackjackapi.exception;

import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.GameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GameException.class)
    public ResponseEntity<String> handleGameExceptions(GameException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        String message = (ex.getMessage() != null) ? ex.getMessage() : "An unexpected error occurred.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + message);
    }
}
