package uz.BTService.btservice.controller.dto.base;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;
import uz.BTService.btservice.entity.role.RoleEnum;


@Data
public class BaseUserDto {

    private String lastname;

    private String middleName;

    private RoleEnum roleEnum;
    public <DTO extends BaseUserDto, ENTITY extends BaseServerModifierEntity> ENTITY toEntity(DTO dto, ENTITY entity, String... ignoreProperties){
        BeanUtils.copyProperties(dto, entity, ignoreProperties);
        return entity;
    }
}
