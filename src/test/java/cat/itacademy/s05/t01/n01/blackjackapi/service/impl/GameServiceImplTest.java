package cat.itacademy.s05.t01.n01.blackjackapi.service.impl;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.PlayerMove;
import cat.itacademy.s05.t01.n01.blackjackapi.enums.GameStatus;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.GameAlreadyEndedException;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.GameNotFoundException;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Game;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.n01.blackjackapi.repository.GameRepository;
import cat.itacademy.s05.t01.n01.blackjackapi.service.PlayerService;
import cat.itacademy.s05.t01.n01.blackjackapi.service.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private GameServiceImpl gameService;

    private Player player;
    private Game game;

    @BeforeEach
    void setUp() {
        player = new Player(1L, "player one", 0);
        game = new Game(player.getId(), player.getName());
    }

    @Test
    void createGame_Success() {
        when(playerService.findByName(player.getName())).thenReturn(Mono.just(player));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.createGame(player.getName()))
                .expectNextMatches(savedGame -> savedGame.getPlayerId().equals(player.getId()) &&
                        savedGame.getPlayerName().equals(player.getName()))
                .verifyComplete();

        verify(playerService, times(1)).findByName(player.getName());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void playGame_Success() {
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(game));
        when(gameRepository.save(any(Game.class))).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.playGame("gameId", PlayerMove.HIT))
                .expectNext(game)
                .verifyComplete();

        verify(gameRepository, times(1)).findById("gameId");
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    void playGame_GameNotFound() {
        when(gameRepository.findById(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(gameService.playGame("gameId", PlayerMove.HIT))
                .expectErrorMatches(throwable -> throwable instanceof GameNotFoundException &&
                        throwable.getMessage().equals("Game not found with id: gameId"))
                .verify();

        verify(gameRepository, times(1)).findById("gameId");
    }

    @Test
    void playGame_GameAlreadyEnded() {
        game.setStatus(GameStatus.FINISHED);
        when(gameRepository.findById(anyString())).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.playGame("gameId", PlayerMove.HIT))
                .expectErrorMatches(throwable -> throwable instanceof GameAlreadyEndedException &&
                        throwable.getMessage().equals("Game already ended with id: gameId"))
                .verify();

        verify(gameRepository, times(1)).findById("gameId");
        verify(gameRepository, never()).save(any(Game.class));
    }

    @Test
    void getAllGames_Success() {
        when(gameRepository.findAll()).thenReturn(Flux.just(game));

        StepVerifier.create(gameService.getAllGames())
                .expectNext(game)
                .verifyComplete();

        verify(gameRepository, times(1)).findAll();
    }

    @Test
    void getGame_Success() {
        when(gameRepository.findById("gameId")).thenReturn(Mono.just(game));

        StepVerifier.create(gameService.getGame("gameId"))
                .expectNext(game)
                .verifyComplete();

        verify(gameRepository, times(1)).findById("gameId");
    }

    @Test
    void getGame_GameNotFound() {
        when(gameRepository.findById("gameId")).thenReturn(Mono.empty());

        StepVerifier.create(gameService.getGame("gameId"))
                .expectErrorMatches(throwable -> throwable instanceof GameNotFoundException &&
                        throwable.getMessage().equals("Game not found with id: gameId"))
                .verify();

        verify(gameRepository, times(1)).findById("gameId");
    }

    @Test
    void deleteGame_Success() {
        when(gameRepository.deleteById("gameId")).thenReturn(Mono.empty());

        StepVerifier.create(gameService.deleteGame("gameId"))
                .verifyComplete();

        verify(gameRepository, times(1)).deleteById("gameId");
    }
}