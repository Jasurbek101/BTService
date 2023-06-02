package uz.BTService.btservice.controller.dto.base;


import lombok.Data;
import org.springframework.beans.BeanUtils;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;


@Data
public class BaseProductDto {

    private String description;

    public <DTO extends BaseProductDto, ENTITY extends BaseServerModifierEntity> ENTITY toEntity(DTO dto, ENTITY entity, String... ignoreProperties){
        BeanUtils.copyProperties(dto, entity, ignoreProperties);
        return entity;
    }
}
