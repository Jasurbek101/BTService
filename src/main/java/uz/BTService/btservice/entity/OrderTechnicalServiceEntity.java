package uz.BTService.btservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.controller.dto.UserDto;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

@Getter
@Setter
@Entity
@Table(name = TableNames.ORDER_TECHNICAL_SERVICE)
public class OrderTechnicalServiceEntity extends BaseServerModifierEntity {

    @Column(name = "technical_service_id")
    private Integer technicalServiceId;

    @ManyToOne
    @JoinColumn(name = "technical_service_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private TechnicalServiceEntity technicalServiceEntity;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserEntity user;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    public UserDto toDto(String... ignoreProperties){
        return toDto(this, new UserDto(), ignoreProperties);
    }
}
