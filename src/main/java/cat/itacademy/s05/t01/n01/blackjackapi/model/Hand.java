package cat.itacademy.s05.t01.n01.blackjackapi.model;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.CardValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Hand {
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    @JsonProperty("score")
    public int getHandValue() {
        return calculateTotal();
    }

    private int calculateTotal() {
        int handValue = cards.stream().mapToInt(Card::getCardValue).sum();
        int acesInHand = (int)cards.stream().filter(card -> card.getValue() == CardValue.ACE).count();

        while (handValue > 21 && acesInHand-- > 0) {
            handValue -= 10;
        }

        return handValue;
    }


    public boolean isBlackjack() {
        return calculateTotal() == 21 && cards.size() == 2;
    }

    public boolean isBust() {
        return calculateTotal() > 21;
    }
}