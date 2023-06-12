package uz.BTService.btservice.controller.dto.request;

import lombok.Getter;
import lombok.Setter;
import uz.BTService.btservice.constants.OrderStatus;

@Getter
@Setter
public class OrderStatusUpdateDto {
    private OrderStatus orderStatus;
}
