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
public class BannerCreateRequestDto {

    @NotBlank(message = "")
    private String name;

    @NotBlank(message = "")
    private int position;

    @NotBlank(message = "")
    private String attachId;
}
