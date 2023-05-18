package uz.BTService.btservice.dto;

import lombok.*;
import uz.BTService.btservice.dto.base.BaseServerModifierDto;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.ProductEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto  extends BaseServerModifierDto {
    private String name;

    private Double price;

    private String color;

    private Integer categoryId;

    private CategoryEntity category;

    private List<String> attachId;
    private List<AttachResponseDTO> attach;

    private String description;

    public ProductEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new ProductEntity(), ignoreProperties);
    }
}
