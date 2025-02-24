package cat.itacademy.s05.t01.n01.blackjackapi.dto;

import cat.itacademy.s05.t01.n01.blackjackapi.enums.PlayerMove;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectMoveRequest {

    @NotBlank(message = "Move cannot be empty.")
    @Schema(description = "Move chosen by the player")
    private PlayerMove move;
}
