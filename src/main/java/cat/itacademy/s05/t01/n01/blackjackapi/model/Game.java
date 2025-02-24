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
@JsonPropertyOrder({"id", "player", "playerHand", "croupierHand", "winner", "status", "remainingDeck"})
public class Game {
    @Id
    private String id;
    private Player player;
    private Hand playerHand;
    private Hand croupierHand;
    private GameStatus status = GameStatus.IN_PROGRESS;
    private Winner winner = Winner.NONE;
    @JsonIgnore
    private List<Card> remainingDeck;

    public Game(Player player) {
        this.player = player;
        this.playerHand = new Hand();
        this.croupierHand = new Hand();
        Deck deck = new Deck();
        drawInitialCards(deck);
        this.remainingDeck = deck.getCards();
    }

    public boolean isBust(boolean isPlayer) {
        return isPlayer ? playerHand.isBust() : croupierHand.isBust();
    }

    public boolean isBlackjack(boolean isPlayer) {
        return isPlayer ? playerHand.isBlackjack() : croupierHand.isBlackjack();
    }

    public void playMove(PlayerMove move) {
        if (status == GameStatus.FINISHED)
            throw new GameAlreadyEndedException("Game already finished.");

        switch (move) {
            case HIT -> hit();
            case STAND -> stand();
            default -> throw new InvalidMoveException("Invalid move. Choose Hit or Stand options.");
        }
    }

    private void drawInitialCards(Deck deck) {
        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        croupierHand.addCard(deck.drawCard());

        if (isBust(true) || isBlackjack(true)) finishGame(Winner.PLAYER);
    }

    private void hit() {
        playerHand.addCard(remainingDeck.remove(0));
        if (isBust(true)) finishGame(Winner.CROUPIER);
        else if (isBlackjack(true)) finishGame(Winner.PLAYER);
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
        Winner finalWinner = isBust(true) || (croupierScore > playerScore && croupierScore <= 21) ? Winner.CROUPIER
                : isBust(false) || (playerScore > croupierScore && playerScore <= 21) ? Winner.PLAYER
                : Winner.TIE;
        finishGame(finalWinner);
    }

    private void finishGame(Winner winner) {
        this.status = GameStatus.FINISHED;
        this.winner = winner;

        if (winner == Winner.PLAYER) {
            player.incrementWins();
        }
    }
}