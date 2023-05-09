package uz.BTService.btservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.PRODUCT)
public class ProductEntity extends BaseServerModifierEntity {

    @ManyToOne
    private CategoryEntity categoryEntity;

    private String name;

    private Double price;







}
