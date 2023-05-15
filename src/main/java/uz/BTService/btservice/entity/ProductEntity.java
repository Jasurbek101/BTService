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
@Table(name = TableNames.PRODUCT)
public class ProductEntity extends BaseServerModifierEntity {

    @ManyToOne(optional = false)
    private CategoryEntity categoryEntity;

    private String name;

    private Double price;


    @OneToOne(optional = false)
    private Avatar avatar;

    private String description;




}
