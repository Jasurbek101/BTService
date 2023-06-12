package uz.BTService.btservice.controller.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForProductCreateDto{

    private Integer productId;

    private double latitude;

    private double longitude;
}
