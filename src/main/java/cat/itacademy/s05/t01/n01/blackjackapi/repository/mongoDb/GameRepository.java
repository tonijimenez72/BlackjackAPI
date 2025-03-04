package cat.itacademy.s05.t01.n01.blackjackapi.repository.mongoDb;

import cat.itacademy.s05.t01.n01.blackjackapi.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
}