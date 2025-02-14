package uz.BTService.btservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.controller.dto.response.ProductResponseForAdminDto;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = TableNames.PRODUCT)
public class ProductEntity extends BaseServerModifierEntity {

    private String name;

    private Double price;

    private String color;


    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CategoryEntity category;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "attach_id" ,referencedColumnName = "id")
    private List<AttachEntity> attach;

    private String description;


    /************************************************************
     * ******************** CONVERT TO DTO ***********************
     * ***********************************************************/
    public ProductResponseForAdminDto toDto(String... ignoreProperties){
        return toDto(this, new ProductResponseForAdminDto(), ignoreProperties);
    }

}
