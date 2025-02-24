package cat.itacademy.s05.t01.n01.blackjackapi.service;

import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerService {
    Mono<Player> updatePlayerName(Long playerId, String newName);
    Flux<Player> getRanking();
}