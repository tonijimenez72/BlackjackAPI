package cat.itacademy.s05.t01.n01.blackjackapi.controller;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.PlayerMove;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.GameAlreadyEndedException;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.InvalidMoveException;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Game;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.n01.blackjackapi.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameService = mock(GameService.class);
        gameController = new GameController(gameService);
    }

    @Test
    void createGame_ShouldReturnGame() {
        Player player = new Player();
        Game game = new Game(player);
        when(gameService.createGame("PlayerOne")).thenReturn(Mono.just(game));

        StepVerifier.create(gameController.createGame("PlayerOne"))
                .expectNext(game)
                .verifyComplete();

        verify(gameService, times(1)).createGame("PlayerOne");
    }

    @Test
    void playMove_ShouldThrowGameAlreadyEndedException() {
        when(gameService.playGame("1", PlayerMove.HIT)).thenReturn(Mono.error(new GameAlreadyEndedException("Game already finished.")));

        StepVerifier.create(gameController.playMove("1", PlayerMove.HIT))
                .expectErrorMessage("Game already finished.")
                .verify();
    }

    @Test
    void playMove_ShouldThrowInvalidMoveException() {
        when(gameService.playGame("1", null)).thenReturn(Mono.error(new InvalidMoveException("Invalid move. Choose Hit or Stand options.")));

        StepVerifier.create(gameController.playMove("1", null))
                .expectErrorMessage("Invalid move. Choose Hit or Stand options.")
                .verify();
    }

    @Test
    void getGame_ShouldReturnGame() {
        Player player = new Player();
        Game game = new Game(player);
        when(gameService.getGame("1")).thenReturn(Mono.just(game));

        StepVerifier.create(gameController.getGame("1"))
                .expectNext(game)
                .verifyComplete();

        verify(gameService, times(1)).getGame("1");
    }

    @Test
    void deleteGame_ShouldComplete() {
        when(gameService.deleteGame("1")).thenReturn(Mono.empty());

        StepVerifier.create(gameController.deleteGame("1"))
                .verifyComplete();

        verify(gameService, times(1)).deleteGame("1");
    }
}