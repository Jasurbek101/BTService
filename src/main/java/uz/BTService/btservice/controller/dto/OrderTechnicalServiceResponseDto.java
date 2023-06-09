package uz.BTService.btservice.controller.dto;

import lombok.Getter;
import lombok.Setter;
import uz.BTService.btservice.controller.dto.base.BaseServerModifierDto;

@Getter
@Setter
public class OrderTechnicalServiceResponseDto extends BaseServerModifierDto {

    private CategoryDto categoryDto;

    private UserDto userDto;
}
