package cat.itacademy.s05.t01.n01.blackjackapi.controller;

import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.n01.blackjackapi.service.PlayerService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
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
        player = new Player(1L, "player one", 1);
    }

    @Test
    void updatePlayerName_Success() {
        when(playerService.updatePlayerName(any(Long.class), any(String.class)))
                .thenReturn(Mono.just(new Player(1L, "player two", 1)));

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/player/{playerId}")
                        .queryParam("playerName", "player two")
                        .build(1L))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Player.class)
                .value(updatedPlayer -> {
                    assertEquals(1L, updatedPlayer.getId());
                    assertEquals("player two", updatedPlayer.getName());
                });

        verify(playerService, times(1)).updatePlayerName(1L, "player two");
    }

    @Test
    void updatePlayerName_Error() {
        when(playerService.updatePlayerName(any(Long.class), any(String.class)))
                .thenReturn(Mono.error(new RuntimeException("Error updating player name")));

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/player/{playerId}")
                        .queryParam("playerName", "player two")
                        .build(1L))
                .exchange()
                .expectStatus().is5xxServerError();

        verify(playerService, times(1)).updatePlayerName(1L, "player two");
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
                    assertFalse(players.isEmpty());
                    assertEquals("player one", players.get(0).getName());
                });

        verify(playerService, times(1)).getRanking();
    }

    @Test
    void getRanking_Error() {
        when(playerService.getRanking()).thenReturn(Flux.error(new RuntimeException("Error retrieving ranking")));

        webTestClient.get()
                .uri("/ranking")
                .exchange()
                .expectStatus().is5xxServerError();

        verify(playerService, times(1)).getRanking();
    }
}