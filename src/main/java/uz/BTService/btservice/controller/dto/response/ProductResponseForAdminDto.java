package uz.BTService.btservice.controller.dto.response;

import lombok.*;
import uz.BTService.btservice.controller.dto.CategoryDto;
import uz.BTService.btservice.controller.dto.base.BaseServerModifierDto;
import uz.BTService.btservice.entity.ProductEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseForAdminDto extends BaseServerModifierDto {

    private String name;

    private Double price;

    private String color;

    private Integer categoryId;

    private CategoryDto category;

    private List<String> attachId;
    private List<AttachResponseDto> attach;

    private String description;

    public ProductEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new ProductEntity(), ignoreProperties);
    }
}
