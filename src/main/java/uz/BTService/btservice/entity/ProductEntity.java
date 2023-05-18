package uz.BTService.btservice.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.dto.ProductDto;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = TableNames.PRODUCT)
public class ProductEntity extends BaseServerModifierEntity {

    private String name;

    private Double price;

    private String color;

    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "attach_id" ,referencedColumnName = "id")
    private List<AttachEntity> attach;

    private String description;


    /************************************************************
     * ******************** CONVERT TO DTO ***********************
     * ***********************************************************/
    public ProductDto toDto(String... ignoreProperties){
        return toDto(this, new ProductDto(), ignoreProperties);
    }

}
