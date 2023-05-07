package uz.BTService.btservice.dto.base;

import lombok.*;
import org.springframework.beans.BeanUtils;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BaseServerModifierDto extends BaseServerDto{
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Long createdBy;

    private Long modifiedBy;

    public <DTO extends BaseServerModifierDto, ENTITY extends BaseServerModifierEntity> ENTITY toEntity(DTO dto, ENTITY entity, String... ignoreProperties){
        BeanUtils.copyProperties(dto, entity, ignoreProperties);
        return entity;
    }
}
