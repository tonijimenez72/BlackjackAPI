package cat.itacademy.s05.t01.n01.blackjackapi.controller;

import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.InvalidRequestException;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.n01.blackjackapi.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PutMapping("/player/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update player name",
            description = "Changes the name for the provided player ID."
    )
    public Mono<Player> updatePlayerName(
            @PathVariable Long playerId,
            @RequestParam("playerName") String playerName
    )
    {
        if (playerName.isBlank() || playerName.isEmpty()){
            throw new InvalidRequestException("Player name cannot be blank.");
        }
        if (!playerName.matches("^[A-Za-z ]{1,50}$")) {
            throw new InvalidRequestException("Only letters and spaces are allowed. Max. size: 50 characters.");
        }

        return playerService.updatePlayerName(playerId, playerName);
    }

    @GetMapping("/ranking")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Show players ranking",
            description = "Shows the ranking of the players."
    )
    public Flux<Player> getRanking() {
        return playerService.getRanking();
    }
}