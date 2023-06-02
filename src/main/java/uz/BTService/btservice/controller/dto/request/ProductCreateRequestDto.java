package uz.BTService.btservice.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.BTService.btservice.controller.dto.base.BaseProductDto;
import uz.BTService.btservice.entity.ProductEntity;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto extends BaseProductDto {

    @NotBlank(message = "name must not be null!!!")
    private String name;

    @NotBlank(message = "price must not be null!!!")
    private Double price;

    @NotBlank(message = "color must not be null!!!")
    private String color;

    @NotBlank(message = "categoryId must not be null!!!")
    private Integer categoryId;

    @NotBlank(message = "attachId must not be null!!!")
    private List<String> attachId;

    public ProductEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new ProductEntity(), ignoreProperties);
    }

}
