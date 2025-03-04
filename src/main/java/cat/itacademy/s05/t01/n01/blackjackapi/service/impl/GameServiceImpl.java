package cat.itacademy.s05.t01.n01.blackjackapi.service.impl;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.PlayerMove;
import cat.itacademy.s05.t01.n01.blackjackapi.enums.GameStatus;
import cat.itacademy.s05.t01.n01.blackjackapi.enums.Winner;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.GameAlreadyEndedException;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.GameNotFoundException;
import cat.itacademy.s05.t01.n01.blackjackapi.model.Game;
import cat.itacademy.s05.t01.n01.blackjackapi.repository.mongoDb.GameRepository;
import cat.itacademy.s05.t01.n01.blackjackapi.service.GameService;
import cat.itacademy.s05.t01.n01.blackjackapi.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final PlayerService playerService;

    @Override
    public Mono<Game> createGame(String playerName) {
        return playerService.findByName(playerName)
                .flatMap(savedPlayer -> {
                    Game game = new Game(savedPlayer.getId(), savedPlayer.getName());
                    return gameRepository.save(game);
                });
    }

    @Override
    public Mono<Game> playGame(String gameId, PlayerMove move) {
        return gameRepository.findById(gameId)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + gameId)))
                .flatMap(game -> {
                    if (game.getStatus() == GameStatus.FINISHED) {
                        return Mono.error(new GameAlreadyEndedException("Game already ended with id: " + gameId));
                    }
                    game.playMove(move);

                    return game.getWinner() == Winner.PLAYER ?
                            playerService.updatePlayerWins(game.getPlayerId()).then(gameRepository.save(game)) :
                            gameRepository.save(game);
                });
    }

    @Override
    public Flux<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Mono<Game> getGame(String id) {
        return gameRepository.findById(id)
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game not found with id: " + id)));
    }

    @Override
    public Mono<Void> deleteGame(String id) {
        return gameRepository.deleteById(id);
    }

}