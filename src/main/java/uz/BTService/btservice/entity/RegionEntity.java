package uz.BTService.btservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.controller.dto.RegionDto;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.REGION)
public class RegionEntity extends BaseServerModifierEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "parentId")
    private Integer parentId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    List<RegionEntity> children = new ArrayList<>();

    @JsonIgnore
    public RegionDto getDto(){
        return getDto(false);
    }
    @JsonIgnore
    public RegionDto getDto(boolean withChildren) {
        RegionDto dto = entityToDto(this, new RegionDto());
        if (this.getChildren() != null && withChildren) {
            this.getChildren().stream()
                    .map(RegionEntity::getDto)
                    .filter(p -> p.getStatus() != EntityStatus.DELETED).collect(Collectors.toList());
        }
        return dto;
    }
    public RegionDto toDto(String... ignoreProperties){
        return toDto(this, new RegionDto(), ignoreProperties);
    }
}
