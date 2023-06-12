package uz.BTService.btservice.controller.convert;

import lombok.experimental.UtilityClass;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.controller.dto.request.OrderForProductCreateDto;
import uz.BTService.btservice.controller.dto.response.OrderForProductResponseDto;
import uz.BTService.btservice.entity.OrderForProductEntity;

import java.util.List;

@UtilityClass
public class OrderForProductConvert {

    public OrderForProductEntity convertToEntity(OrderForProductCreateDto orderForProductCreateDto){
        OrderForProductEntity orderForProduct = new OrderForProductEntity();
        Integer userId = SecurityUtils.getUserId();
        orderForProduct.setProductId(orderForProductCreateDto.getProductId());
        orderForProduct.setLatitude(orderForProductCreateDto.getLatitude());
        orderForProduct.setLongitude(orderForProductCreateDto.getLongitude());
        orderForProduct.setUserId(userId);

        orderForProduct.forCreate(userId);

        return orderForProduct;
    }

    public OrderForProductResponseDto from(OrderForProductEntity product){
        return product.toDto();
    }

    public List<OrderForProductResponseDto> from(List<OrderForProductEntity> orderForProductEntityList){
        return orderForProductEntityList.stream().map(OrderForProductConvert::from).toList();
    }
}
