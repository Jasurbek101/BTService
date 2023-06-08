package uz.BTService.btservice.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalServiceCreate {
    @NotBlank(message = "description must not be null!!!")
    private String description;

    @NotBlank(message = "attachId must not be null!!!")
    private String attachId;

    @NotBlank(message = "categoryId must not be null!!!")
    private Integer categoryId;
}
