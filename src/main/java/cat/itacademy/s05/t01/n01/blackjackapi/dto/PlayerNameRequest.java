package cat.itacademy.s05.t01.n01.blackjackapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlayerNameRequest {

    @NotNull(message = "Player name cannot be null.")
    @NotBlank(message = "Player name cannot be empty.")
    @Schema(
            title = "Player name",
            description = "Enter name:",
            example = "Jane Doe"
    )
    private String playerName;
}