package cat.itacademy.s05.t01.n01.blackjackapi.controller;

import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.n01.blackjackapi.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    private WebTestClient webTestClient;

    private Player player;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(playerController).build();
        player = new Player(1L, "John Doe", 5);
    }

    @Test
    void updatePlayerName_Success() {
        when(playerService.updatePlayerName(any(Long.class), any(String.class)))
                .thenReturn(Mono.just(new Player(1L, "Jane Doe", 5)));

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/player/{playerId}").queryParam("playerName", "Jane Doe").build(1L))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Player.class)
                .value(updatedPlayer -> {
                    assert updatedPlayer.getId().equals(1L);
                    assert updatedPlayer.getName().equals("Jane Doe");
                    assert updatedPlayer.getPlayerWinsCounter() == 5;
                });

        verify(playerService, times(1)).updatePlayerName(1L, "Jane Doe");
    }

    @Test
    void getRanking_Success() {
        when(playerService.getRanking()).thenReturn(Flux.just(player));

        webTestClient.get()
                .uri("/ranking")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Player.class)
                .value(players -> {
                    assert !players.isEmpty();
                    assert players.get(0).getName().equals("John Doe");
                });

        verify(playerService, times(1)).getRanking();
    }
}
