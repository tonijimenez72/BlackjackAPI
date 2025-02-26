package cat.itacademy.s05.t01.n01.blackjackapi.model;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.Winner;
import cat.itacademy.s05.t01.n01.blackjackapi.enums.GameStatus;
import cat.itacademy.s05.t01.n01.blackjackapi.enums.PlayerMove;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.GameAlreadyEndedException;
import cat.itacademy.s05.t01.n01.blackjackapi.exception.custom.InvalidMoveException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "games")
@JsonPropertyOrder({"id", "playerId", "playerName", "playerHand", "croupierHand", "winner", "status"})
public class Game {
    @Id
    private String id;
    private Long playerId;
    private String playerName;
    private Hand playerHand;
    private Hand croupierHand;
    private GameStatus status = GameStatus.IN_PROGRESS;
    private Winner winner = Winner.NONE;

    @JsonIgnore
    private List<Card> remainingDeck;

    public Game(Long playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerHand = new Hand();
        this.croupierHand = new Hand();
        Deck deck = new Deck();
        initializeGame(deck);
        this.remainingDeck = deck.getCards();
    }

    public void playMove(PlayerMove move) {
        if (status == GameStatus.FINISHED) throw new GameAlreadyEndedException("Game already finished.");

        switch (move) {
            case HIT -> hit();
            case STAND -> stand();
            default -> throw new InvalidMoveException("Invalid move. Choose Hit or Stand options.");
        }
    }

    private void initializeGame(Deck deck) {
        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        croupierHand.addCard(deck.drawCard());

        if (playerHand.isBlackjack()) finishGame(Winner.PLAYER);
    }

    private void hit() {
        playerHand.addCard(remainingDeck.remove(0));

        if (playerHand.isBust()) finishGame(Winner.CROUPIER);
    }

    private void stand() {
        while (croupierHand.getHandValue() < 17 && !remainingDeck.isEmpty()) {
            croupierHand.addCard(remainingDeck.remove(0));
        }
        determineWinner();
    }

    private void determineWinner() {
        int playerScore = playerHand.getHandValue();
        int croupierScore = croupierHand.getHandValue();

        Winner finalWinner =
                (croupierHand.isBust() || (croupierScore > playerScore && croupierScore <= 21))? Winner.CROUPIER :
                (playerHand.isBust() || (playerScore > croupierScore && playerScore <= 21))? Winner.PLAYER :
                Winner.TIE;

        finishGame(finalWinner);
    }

    private void finishGame(Winner winner) {
        this.status = GameStatus.FINISHED;
        this.winner = winner;
    }
}