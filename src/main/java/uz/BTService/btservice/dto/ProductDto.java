package uz.BTService.btservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import uz.BTService.btservice.dto.base.BaseServerModifierDto;
import uz.BTService.btservice.entity.AttachEntity;
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

    private CategoryDto category;

    private List<String> attachId;
    private List<AttachResponseDTO> attach;

    private String description;

    public ProductEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new ProductEntity(), ignoreProperties);
    }
}
