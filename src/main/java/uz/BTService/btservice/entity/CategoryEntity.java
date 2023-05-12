package uz.BTService.btservice.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.CATEGORY)
public class CategoryEntity extends BaseServerModifierEntity {

    @Column(unique = true,nullable = false)
    private String name;

}
