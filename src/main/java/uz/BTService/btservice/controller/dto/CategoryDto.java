package uz.BTService.btservice.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.BTService.btservice.constants.CategoryType;
import uz.BTService.btservice.controller.dto.base.BaseServerModifierDto;
import uz.BTService.btservice.entity.CategoryEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto extends BaseServerModifierDto {

    @NotBlank(message = "name must not be empty")
    private String name;

    private Integer parentId;

    private CategoryType type;

    private List<CategoryDto> children;

    public CategoryEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new CategoryEntity(), ignoreProperties);
    }
}
