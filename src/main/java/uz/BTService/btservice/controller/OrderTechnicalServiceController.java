package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.controller.convert.OrderForServiceConvert;
import uz.BTService.btservice.controller.dto.response.OrderForServiceResponseDto;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.request.OrderForServiceCreateDto;
import uz.BTService.btservice.controller.dto.request.OrderStatusUpdateDto;
import uz.BTService.btservice.entity.OrderTechnicalForServiceEntity;
import uz.BTService.btservice.service.OrderTechnicalService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-for-service")
@RequiredArgsConstructor
public class OrderTechnicalServiceController {

    private final OrderTechnicalService service;


    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/add")
    public HttpResponse<Object> addOrderForService(@RequestBody OrderForServiceCreateDto orderForServiceCreateDto) {

        HttpResponse<Object> response = HttpResponse.build(true);
        OrderTechnicalForServiceEntity orderTechnicalService = OrderForServiceConvert.convertToEntity(orderForServiceCreateDto);
        boolean addOrder = service.addOrder(orderTechnicalService);

        return response
                .code(HttpResponse.Status.OK)
                .body(addOrder)
                .message(HttpResponse.Status.OK.name());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('CALL_CENTER_FOR_SERVICE','SUPER_ADMIN')")
    @Operation(summary = "This method for get by id", description = "This method get by id")
    @GetMapping("/get/{id}")
    public HttpResponse<Object> getOrderForService(@PathVariable Integer id) {

        HttpResponse<Object> response = HttpResponse.build(true);
        OrderForServiceResponseDto orderForServiceResponseDto = OrderForServiceConvert.from(service.getOrderById(id));

        return response
                .code(HttpResponse.Status.OK)
                .body(orderForServiceResponseDto)
                .message(HttpResponse.Status.OK.name());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('CALL_CENTER_FOR_SERVICE','SUPER_ADMIN')")
    @Operation(summary = "This method for get all", description = "This method get all order service")
    @GetMapping("/get/all")
    public HttpResponse<Object> getOrderForServiceAll() {

        HttpResponse<Object> response = HttpResponse.build(true);
        List<OrderTechnicalForServiceEntity> orderTechnicalForServiceEntityList = service.getAllOrderForService();
        List<OrderForServiceResponseDto> orderTechnicalServiceEntities = OrderForServiceConvert.from(orderTechnicalForServiceEntityList);

        return response
                .code(HttpResponse.Status.OK)
                .body(orderTechnicalServiceEntities)
                .message(HttpResponse.Status.OK.name());
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('CALL_CENTER_FOR_SERVICE','SUPER_ADMIN')")
    @Operation(summary = "This method for put", description = "This method update order status")
    @PutMapping("/update/status/{id}")
    public HttpResponse<Object> updateOrderStatus(@RequestBody OrderStatusUpdateDto orderStatusUpdateDto, @PathVariable Integer id) {

        HttpResponse<Object> response = HttpResponse.build(true);
        boolean updateOrderStatus = service.updateOrderStatus(orderStatusUpdateDto.getOrderStatus(), id);

        return response
                .code(HttpResponse.Status.OK)
                .body(updateOrderStatus)
                .message(HttpResponse.Status.OK.name());
    }



}
