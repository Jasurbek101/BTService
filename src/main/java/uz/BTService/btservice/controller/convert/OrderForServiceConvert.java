package uz.BTService.btservice.controller.convert;

import lombok.experimental.UtilityClass;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.controller.dto.request.OrderForServiceCreateDto;
import uz.BTService.btservice.entity.OrderTechnicalServiceEntity;

@UtilityClass
public class OrderForServiceConvert {

    public OrderTechnicalServiceEntity convertToEntity(OrderForServiceCreateDto orderForServiceCreateDto){
        OrderTechnicalServiceEntity orderTechnicalService = new OrderTechnicalServiceEntity();

        orderTechnicalService.setTechnicalServiceId(orderForServiceCreateDto.getTechnicalServiceId());
        orderTechnicalService.forCreate(SecurityUtils.getUserId());
        orderTechnicalService.setLatitude(orderForServiceCreateDto.getLatitude());
        orderTechnicalService.setLongitude(orderForServiceCreateDto.getLongitude());

        return orderTechnicalService;
    }
}
