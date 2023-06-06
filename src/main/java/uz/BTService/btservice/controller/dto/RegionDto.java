package uz.BTService.btservice.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.BTService.btservice.controller.dto.base.BaseServerModifierDto;
import uz.BTService.btservice.entity.RegionEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionDto extends BaseServerModifierDto {
    @NotBlank(message = "name must not be empty")
    private String name;

    private Integer parentId;

    private List<RegionDto> children;

    public RegionEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new RegionEntity(), ignoreProperties);
    }
}
