package uz.BTService.btservice.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.BTService.btservice.controller.dto.CategoryDto;
import uz.BTService.btservice.controller.dto.base.BaseServerModifierDto;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalServiceResponseDto extends BaseServerModifierDto {

    private String description;

    private AttachResponseDto attachResponse;

    private CategoryDto category;

}
