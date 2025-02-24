package cat.itacademy.s05.t01.n01.blackjackapi.service;

import cat.itacademy.s05.t01.n01.blackjackapi.model.Game;
import cat.itacademy.s05.t01.n01.blackjackapi.enums.PlayerMove;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameService {
    Mono<Game> createGame(String playerName);
    Mono<Game> playGame(String gameId, PlayerMove move);
    Flux<Game> getAllGames();
    Mono<Game> getGame(String id);
    Mono<Void> deleteGame(String id);
}