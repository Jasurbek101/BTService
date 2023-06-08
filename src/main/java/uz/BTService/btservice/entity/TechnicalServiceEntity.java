package uz.BTService.btservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.controller.dto.UserDto;
import uz.BTService.btservice.controller.dto.response.TechnicalServiceResponseDto;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.TECHNICAL_SERVICE)
public class TechnicalServiceEntity extends BaseServerModifierEntity {

    @Column(columnDefinition="TEXT")
    private String description;

    @Column(name = "attach_id")
    private String attachId;

    @ManyToOne
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "category_id")
    private Integer categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CategoryEntity category;

    public TechnicalServiceResponseDto toDto(String... ignoreProperties){
        return toDto(this, new TechnicalServiceResponseDto(), ignoreProperties);
    }
}
