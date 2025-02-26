package cat.itacademy.s05.t01.n01.blackjackapi.dto;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.PlayerMove;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectMoveRequest {
    private PlayerMove move;
}
