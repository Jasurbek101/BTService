package uz.BTService.btservice.controller.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForServiceCreateDto {

    private Integer technicalServiceId;

    private double latitude;
    private double longitude;

}
