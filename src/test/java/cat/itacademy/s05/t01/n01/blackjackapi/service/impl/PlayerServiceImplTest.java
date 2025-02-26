package cat.itacademy.s05.t01.n01.blackjackapi.service.impl;

import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.PlayerNotFoundException;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.n01.blackjackapi.repository.PlayerRepository;
import cat.itacademy.s05.t01.n01.blackjackapi.service.impl.PlayerServiceImpl;
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
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(1L, "player one", 0);
    }

    @Test
    void findByName_PlayerExists() {
        when(playerRepository.findByName(player.getName())).thenReturn(Mono.just(player));

        StepVerifier.create(playerService.findByName(player.getName()))
                .expectNextMatches(foundPlayer -> foundPlayer.getName().equals(player.getName()))
                .verifyComplete();

        verify(playerRepository, times(1)).findByName(player.getName());
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    void findByName_PlayerNotExists_CreatesNew() {
        when(playerRepository.findByName(player.getName())).thenReturn(Mono.empty());
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));

        StepVerifier.create(playerService.findByName(player.getName()))
                .expectNextMatches(newPlayer -> newPlayer.getName().equals(player.getName()))
                .verifyComplete();

        verify(playerRepository, times(1)).findByName(player.getName());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void updatePlayerName_Success() {
        when(playerRepository.findById(player.getId())).thenReturn(Mono.just(player));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));

        StepVerifier.create(playerService.updatePlayerName(player.getId(), "player two"))
                .expectNextMatches(updatedPlayer -> updatedPlayer.getName().equals("player two"))
                .verifyComplete();

        verify(playerRepository, times(1)).findById(player.getId());
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void updatePlayerName_PlayerNotFound() {
        when(playerRepository.findById(player.getId())).thenReturn(Mono.empty());

        StepVerifier.create(playerService.updatePlayerName(player.getId(), "player two"))
                .expectErrorMatches(throwable -> throwable instanceof PlayerNotFoundException &&
                        throwable.getMessage().equals("Player not found with id: " + player.getId()))
                .verify();

        verify(playerRepository, times(1)).findById(player.getId());
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    void getRanking_Success() {
        when(playerRepository.findAll()).thenReturn(Flux.just(player));

        StepVerifier.create(playerService.getRanking())
                .expectNext(player)
                .verifyComplete();

        verify(playerRepository, times(1)).findAll();
    }

    @Test
    void updatePlayerWins_Success() {
        when(playerRepository.findById(player.getId())).thenReturn(Mono.just(player));
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));

        StepVerifier.create(playerService.updatePlayerWins(player.getId()))
                .expectNextMatches(updatedPlayer -> updatedPlayer.getPlayerWinsCounter() == 1)
                .verifyComplete();

        verify(playerRepository, times(1)).findById(player.getId());
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void updatePlayerWins_PlayerNotFound() {
        when(playerRepository.findById(player.getId())).thenReturn(Mono.empty());

        StepVerifier.create(playerService.updatePlayerWins(player.getId()))
                .expectErrorMatches(throwable -> throwable instanceof PlayerNotFoundException &&
                        throwable.getMessage().equals("Player not found with id: " + player.getId()))
                .verify();

        verify(playerRepository, times(1)).findById(player.getId());
        verify(playerRepository, never()).save(any(Player.class));
    }
}