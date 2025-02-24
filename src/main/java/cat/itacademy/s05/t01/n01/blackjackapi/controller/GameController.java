package cat.itacademy.s05.t01.n01.blackjackapi.controller;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.PlayerMove;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.InvalidRequestException;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Game;
import cat.itacademy.s05.t01.n01.blackjackapi.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new game",
            description = "Creates a new game session for the provided player name."
    )

    public Mono<Game> createGame(
            @RequestParam("playerName")String playerName
    ) {
        if (playerName.isBlank() || playerName.isEmpty()){
            throw new InvalidRequestException("Player name cannot be blank.");
        }
        if (!playerName.matches("^[A-Za-z ]{1,50}$")) {
            throw new InvalidRequestException("Only letters and spaces are allowed. Max. size: 50 characters.");
        }
        return gameService.createGame(playerName);
    }

    @PostMapping("/{id}/play")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Play a move",
            description = "Executes a player move (HIT or STAND) in an existing game."
    )
    public Mono<Game> playMove(
            @PathVariable String id,
            @RequestParam @Schema(
                    enumAsRef = true,
                    description = "Select a move (HIT or STAND)"
            ) PlayerMove move
    ) {
        return gameService.playGame(id, move);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get game details",
            description = "Shows details of a specific game session."
    )
    public Mono<Game> getGame(@PathVariable String id) {
        return gameService.getGame(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get all games",
            description = "Shows a list of all existing game sessions."
    )
    public Flux<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a game",
            description = "Deletes an existing game session by its ID."
    )
    public Mono<Void> deleteGame(@PathVariable String id) {
        return gameService.deleteGame(id);
    }
}