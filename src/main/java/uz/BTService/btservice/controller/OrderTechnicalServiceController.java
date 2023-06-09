package uz.BTService.btservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.BTService.btservice.controller.convert.OrderForServiceConvert;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.request.OrderForServiceCreateDto;
import uz.BTService.btservice.entity.OrderTechnicalServiceEntity;
import uz.BTService.btservice.service.OrderTechnicalService;

@RestController
@RequestMapping("/api/v1/order-for-service")
@RequiredArgsConstructor
public class OrderTechnicalServiceController {

    private final OrderTechnicalService service;

    @PostMapping("add")
    public HttpResponse<Object> addOrderForService(@RequestBody OrderForServiceCreateDto orderForServiceCreateDto){

        HttpResponse<Object> response = HttpResponse.build(false);
        OrderTechnicalServiceEntity orderTechnicalService = OrderForServiceConvert.convertToEntity(orderForServiceCreateDto);
        boolean addOrder = service.addOrder(orderTechnicalService);

        return response
                .code(HttpResponse.Status.OK)
                .body(addOrder)
                .message(HttpResponse.Status.OK.name());
    }
}
