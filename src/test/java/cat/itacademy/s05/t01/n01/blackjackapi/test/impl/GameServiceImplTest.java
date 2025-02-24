package cat.itacademy.s05.t01.n01.blackjackapi.test.impl;

import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.GameNotFoundException;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.InvalidMoveException;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.PlayerNotFoundException;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Game;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.n01.blackjackapi.enums.PlayerMove;
import cat.itacademy.s05.t01.n01.blackjackapi.service.GameService;
import cat.itacademy.s05.t01.n01.blackjackapi.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameService gameService;

    @Mock
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        gameService = mock(GameService.class);
        playerService = mock(PlayerService.class);
    }

    @Test
    void createGame_ShouldReturnGame() {
        Game game = new Game();
        when(gameService.createGame("Player1")).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.createGame("Player1"))
                .expectNext(game)
                .verifyComplete();

        verify(gameService, times(1)).createGame("Player1");
    }

    @Test
    void playGame_ShouldReturnUpdatedGame() {
        Game game = new Game();
        when(gameService.playGame("1", PlayerMove.HIT)).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.playGame("1", PlayerMove.HIT))
                .expectNext(game)
                .verifyComplete();

        verify(gameService, times(1)).playGame("1", PlayerMove.HIT);
    }

    @Test
    void playGame_ShouldHandleError() {
        when(gameService.playGame("1", PlayerMove.HIT)).thenReturn(Mono.error(new InvalidMoveException("Invalid move")));

        StepVerifier.create(gameService.playGame("1", PlayerMove.HIT))
                .expectErrorMessage("Invalid move")
                .verify();
    }

    @Test
    void getAllGames_ShouldReturnListOfAllGames() {
        Game game1 = new Game();
        Game game2 = new Game();
        when(gameService.getAllGames()).thenReturn(Flux.just(game1, game2));

        StepVerifier.create(gameService.getAllGames())
                .expectNext(game1, game2)
                .verifyComplete();

        verify(gameService, times(1)).getAllGames();
    }

    @Test
    void getGame_ShouldReturnGame() {
        Game game = new Game();
        when(gameService.getGame("1")).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.getGame("1"))
                .expectNext(game)
                .verifyComplete();

        verify(gameService, times(1)).getGame("1");
    }

    @Test
    void getGame_ShouldHandleError() {
        when(gameService.getGame("1")).thenReturn(Mono.error(new GameNotFoundException("Game not found")));

        StepVerifier.create(gameService.getGame("1"))
                .expectErrorMessage("Game not found")
                .verify();
    }

    @Test
    void deleteGame_ShouldComplete() {
        when(gameService.deleteGame("1")).thenReturn(Mono.empty());

        StepVerifier.create(gameService.deleteGame("1"))
                .verifyComplete();

        verify(gameService, times(1)).deleteGame("1");
    }

    @Test
    void updatePlayerName_ShouldReturnUpdatedPlayer() {
        Player player = new Player();
        when(playerService.updatePlayerName(1L, "NewName")).thenReturn(Mono.just(player));

        StepVerifier.create(playerService.updatePlayerName(1L, "NewName"))
                .expectNext(player)
                .verifyComplete();

        verify(playerService, times(1)).updatePlayerName(1L, "NewName");
    }

    @Test
    void updatePlayerName_ShouldHandleError() {
        when(playerService.updatePlayerName(1L, "New Name")).thenReturn(Mono.error(new PlayerNotFoundException("Player not found")));

        StepVerifier.create(playerService.updatePlayerName(1L, "New Name"))
                .expectErrorMessage("Player not found")
                .verify();
    }

    @Test
    void getRanking_ShouldReturnFluxOfPlayers() {
        Player player1 = new Player();
        Player player2 = new Player();
        when(playerService.getRanking()).thenReturn(Flux.just(player1, player2));

        StepVerifier.create(playerService.getRanking())
                .expectNext(player1, player2)
                .verifyComplete();

        verify(playerService, times(1)).getRanking();
    }
}
