package cat.itacademy.s05.t01.n01.blackjackapi.service.impl;

import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.PlayerNotFoundException;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import cat.itacademy.s05.t01.n01.blackjackapi.repository.mySQL.PlayerRepository;
import cat.itacademy.s05.t01.n01.blackjackapi.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public Mono<Player> findByName(String playerName) {
        return playerRepository.findByName(playerName)
                .switchIfEmpty(Mono.defer(() -> {
                    Player newPlayer = new Player();
                    newPlayer.setName(playerName);
                    newPlayer.setPlayerWinsCounter(0);
                    return playerRepository.save(newPlayer);
                }));
    }

    @Override
    public Mono<Player> updatePlayerName(Long playerId, String newName) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with id: " + playerId)))
                .flatMap(player -> {
                    player.setName(newName);
                    return playerRepository.save(player);
                });
    }

    @Override
    public Flux<Player> getRanking() {
        return playerRepository.findAllByOrderByPlayerWinsCounterDesc();
    }

    @Override
    public Mono<Player> updatePlayerWins(Long playerId) {
        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player not found with id: " + playerId)))
                .flatMap(player -> {
                    player.incrementWins();
                    return playerRepository.save(player);
                });
    }

}