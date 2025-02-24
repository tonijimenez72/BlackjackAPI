package cat.itacademy.s05.t01.n01.blackjackapi.controller;

import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.n01.blackjackapi.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        playerService = mock(PlayerService.class);
        playerController = new PlayerController(playerService);
    }

    @Test
    void updatePlayerName_ShouldReturnUpdatedPlayer() {
        Player player = new Player();
        when(playerService.updatePlayerName(1L, "NewName")).thenReturn(Mono.just(player));

        StepVerifier.create(playerController.updatePlayerName(1L, "NewName"))
                .expectNext(player)
                .verifyComplete();

        verify(playerService, times(1)).updatePlayerName(1L, "NewName");
    }

    @Test
    void getRanking_ShouldReturnRankingOfAllPlayers() {
        Player player1 = new Player();
        Player player2 = new Player();
        when(playerService.getRanking()).thenReturn(Flux.just(player1, player2));

        StepVerifier.create(playerController.getRanking())
                .expectNext(player1, player2)
                .verifyComplete();

        verify(playerService, times(1)).getRanking();
    }

}