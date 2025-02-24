package cat.itacademy.s05.t01.n01.blackjackapi.model;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.CardSuit;
import cat.itacademy.s05.t01.n01.blackjackapi.enums.CardValue;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
public class Card {
    @JsonIgnore
    private CardValue value;
    @JsonIgnore
    private CardSuit suit;

    public int getCardValue() {
        return value.getValue();
    }

    @JsonGetter("card")
    public String getCard() {
        return value.toString() + " of " + suit.toString();
    }
}