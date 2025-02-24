package cat.itacademy.s05.t01.n01.blackjackapi.test.impl;

import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.PlayerNotFoundException;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.n01.blackjackapi.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        playerService = Mockito.mock(PlayerService.class);
    }

    @Test
    void updatePlayerName_ShouldReturnUpdatedPlayer() {
        Player player = new Player();
        Mockito.when(playerService.updatePlayerName(1L, "NewName")).thenReturn(Mono.just(player));

        StepVerifier.create(playerService.updatePlayerName(1L, "NewName"))
                .expectNext(player)
                .verifyComplete();

        Mockito.verify(playerService, Mockito.times(1)).updatePlayerName(1L, "NewName");
    }

    @Test
    void updatePlayerName_ShouldHandleError() {
        Mockito.when(playerService.updatePlayerName(1L, "NewName")).thenReturn(Mono.error(new PlayerNotFoundException("Player not found")));

        StepVerifier.create(playerService.updatePlayerName(1L, "NewName"))
                .expectErrorMessage("Player not found")
                .verify();
    }

    @Test
    void getRanking_ShouldReturnRewnkingOfPlayers() {
        Player player1 = new Player();
        Player player2 = new Player();
        Mockito.when(playerService.getRanking()).thenReturn(Flux.just(player1, player2));

        StepVerifier.create(playerService.getRanking())
                .expectNext(player1, player2)
                .verifyComplete();

        Mockito.verify(playerService, Mockito.times(1)).getRanking();
    }
}