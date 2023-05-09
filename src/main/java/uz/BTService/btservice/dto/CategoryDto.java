package uz.BTService.btservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.BTService.btservice.dto.base.BaseServerModifierDto;
import uz.BTService.btservice.entity.CategoryEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends BaseServerModifierDto {

    @NotBlank(message = "name must not be empty")
    private String name;

    public CategoryEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new CategoryEntity(), ignoreProperties);
    }
}
