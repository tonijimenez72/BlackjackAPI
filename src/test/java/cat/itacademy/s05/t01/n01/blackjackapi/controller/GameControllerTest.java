package cat.itacademy.s05.t01.n01.blackjackapi.controller;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.PlayerMove;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.InvalidRequestException;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Game;
import cat.itacademy.s05.t01.n01.blackjackapi.service.GameService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    /*@BeforeAll
    public static void loadEnv() {
        Dotenv dotenv = Dotenv.configure().load();
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }

     */

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createGame_Success() {
        String playerName = "player one";
        Game mockGame = new Game(1L, playerName);

        when(gameService.createGame(playerName)).thenReturn(Mono.just(mockGame));

        Mono<Game> result = gameController.createGame(playerName);

        StepVerifier.create(result)
                .expectNext(mockGame)
                .verifyComplete();

        verify(gameService, times(1)).createGame(playerName);
    }

    @Test
    void createGame_InvalidName_ThrowsException() {
        String invalidName = "";

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            gameController.createGame(invalidName);
        });

        assertEquals("Player name cannot be blank.", exception.getMessage());
    }

    @Test
    void playMove_Success() {
        String gameId = "gameone";
        PlayerMove move = PlayerMove.HIT;
        Game mockGame = new Game(1L, "player one");

        when(gameService.playGame(gameId, move)).thenReturn(Mono.just(mockGame));

        Mono<Game> result = gameController.playMove(gameId, move);

        StepVerifier.create(result)
                .expectNext(mockGame)
                .verifyComplete();

        verify(gameService, times(1)).playGame(gameId, move);
    }

    @Test
    void getGame_Success() {
        String gameId = "gameone";
        Game mockGame = new Game(1L, "player one");

        when(gameService.getGame(gameId)).thenReturn(Mono.just(mockGame));

        Mono<Game> result = gameController.getGame(gameId);

        StepVerifier.create(result)
                .expectNext(mockGame)
                .verifyComplete();

        verify(gameService, times(1)).getGame(gameId);
    }

    @Test
    void getAllGames_Success() {
        Game game1 = new Game(1L, "player one");
        Game game2 = new Game(2L, "player two");

        when(gameService.getAllGames()).thenReturn(Flux.just(game1, game2));

        Flux<Game> result = gameController.getAllGames();

        StepVerifier.create(result)
                .expectNext(game1)
                .expectNext(game2)
                .verifyComplete();

        verify(gameService, times(1)).getAllGames();
    }

    @Test
    void deleteGame_Success() {
        String gameId = "gameone";

        when(gameService.deleteGame(gameId)).thenReturn(Mono.empty());

        Mono<Void> result = gameController.deleteGame(gameId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(gameService, times(1)).deleteGame(gameId);
    }

    @Test
    void createGame_InvalidCharacters_ThrowsException() {
        String invalidName = "trololó";

        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
            gameController.createGame(invalidName);
        });

        assertEquals("Only letters and spaces are allowed. Max. size: 50 characters.", exception.getMessage());
    }
}