package uz.BTService.btservice.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.dto.base.BaseServerModifierDto;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass

public class BaseServerModifierEntity extends BaseServerEntity{
    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Column(name = "updatedDate")
    private LocalDateTime updatedDate;

    @Column(name = "createdBy")
    private Integer createdBy;

    @Column(name = "modifiedBy")
    private Integer modifiedBy;

    public void forCreate(){
        forCreate(null);
    }
    public void forCreate(Integer creatorId){
        this.setCreatedBy(creatorId);
        this.setCreatedDate(LocalDateTime.now());
        this.setStatus(EntityStatus.CREATED);
    }

    public void forUpdate(){
        forUpdate(null);
    }

    public void forUpdate(Integer modifierId){
        this.setModifiedBy(modifierId);
        this.setUpdatedDate(LocalDateTime.now());
        this.setStatus(EntityStatus.UPDATED);
    }

    /*
    ********************* CONVERT TO DTO ****************************
    * */
    public <ENTITY extends BaseServerModifierEntity, DTO extends BaseServerModifierDto> DTO toDto(ENTITY entity, DTO dto, String... ignoreProperties){
        BeanUtils.copyProperties(entity, dto, ignoreProperties);
        return dto;
    }
}
