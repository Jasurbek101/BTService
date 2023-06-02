package uz.BTService.btservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.controller.dto.CategoryDto;
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
@Table(name = TableNames.CATEGORY)
public class CategoryEntity extends BaseServerModifierEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "parentId")
    private Integer parentId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    List<CategoryEntity> children = new ArrayList<>();

    @JsonIgnore
    public CategoryDto getDto(){
        return getDto(false);
    }
    @JsonIgnore
    public CategoryDto getDto(boolean withChildren) {
        CategoryDto dto = entityToDto(this, new CategoryDto());
        if (this.getChildren() != null && withChildren) {
            dto.setChildren(
                    this.getChildren().stream()
                            .map(CategoryEntity::getDto)
                            .filter(p -> p.getStatus() != EntityStatus.DELETED).collect(Collectors.toList()));
        }
        return dto;
    }
    public CategoryDto toDto(String... ignoreProperties){
        return toDto(this, new CategoryDto(), ignoreProperties);
    }
}
