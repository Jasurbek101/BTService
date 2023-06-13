package uz.BTService.btservice.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.controller.dto.RegionDto;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    public RegionDto toDto(String... ignoreProperties){
        return toDto(this, new RegionDto(), ignoreProperties);
    }
}
