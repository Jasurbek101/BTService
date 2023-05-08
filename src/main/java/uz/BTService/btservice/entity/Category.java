package uz.BTService.btservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.CATEGORY)
public class Category extends BaseServerModifierEntity {
    private String name;
    private Double price;
}
