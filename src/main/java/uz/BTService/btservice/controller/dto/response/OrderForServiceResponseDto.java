package uz.BTService.btservice.controller.dto.response;

import lombok.Getter;
import lombok.Setter;
import uz.BTService.btservice.constants.OrderStatus;
import uz.BTService.btservice.controller.dto.base.BaseServerModifierDto;
import uz.BTService.btservice.entity.OrderForProductEntity;
import uz.BTService.btservice.entity.OrderTechnicalForServiceEntity;
import uz.BTService.btservice.entity.TechnicalServiceEntity;
import uz.BTService.btservice.entity.UserEntity;

@Getter
@Setter
public class OrderForServiceResponseDto extends BaseServerModifierDto {

    private TechnicalServiceEntity technicalServiceEntity;

    private OrderStatus orderStatus;

    private UserEntity user;

    private double latitude;

    private double longitude;

    public OrderTechnicalForServiceEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new OrderTechnicalForServiceEntity(), ignoreProperties);
    }
}
