package cat.itacademy.s05.t01.n01.blackjackapi.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true, description = "Possible moves in Blackjack: HIT (Draw a card) or STAND (Hold position).")
public enum PlayerMove {
    HIT,
    STAND
}