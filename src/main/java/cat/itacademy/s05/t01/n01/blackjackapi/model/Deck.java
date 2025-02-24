package cat.itacademy.s05.t01.n01.blackjackapi.model;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.CardSuit;
import cat.itacademy.s05.t01.n01.blackjackapi.enums.CardValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = initializeDeck();
        shuffle();
    }

    private List<Card> initializeDeck() {
        List<Card> deck = new ArrayList<>();
        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                deck.add(new Card(value, suit));
            }
        }
        return deck;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        return cards.remove(0);
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}