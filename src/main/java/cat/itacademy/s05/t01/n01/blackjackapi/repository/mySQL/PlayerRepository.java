package cat.itacademy.s05.t01.n01.blackjackapi.repository.mySQL;

import cat.itacademy.s05.t01.n01.blackjackapi.model.Player;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends R2dbcRepository<Player, Long> {
    Mono<Player> findByName(String name);
    Flux<Player> findAllByOrderByPlayerWinsCounterDesc();
}
