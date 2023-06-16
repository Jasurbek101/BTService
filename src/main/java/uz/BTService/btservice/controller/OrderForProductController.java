package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.controller.convert.OrderForProductConvert;
import uz.BTService.btservice.controller.dto.request.OrderForProductCreateDto;
import uz.BTService.btservice.controller.dto.response.OrderForProductResponseDto;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.request.OrderStatusUpdateDto;
import uz.BTService.btservice.entity.OrderForProductEntity;
import uz.BTService.btservice.service.OrderForProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-for-product")
@RequiredArgsConstructor
public class OrderForProductController {


    private final OrderForProductService service;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("permitAll()")
    @PostMapping("/add")
    public HttpResponse<Object> addOrderForService(@RequestBody OrderForProductCreateDto orderForProductCreateDto) {

        HttpResponse<Object> response = HttpResponse.build(true);
        OrderForProductEntity orderForProduct = OrderForProductConvert.convertToEntity(orderForProductCreateDto);
        boolean addOrder = service.addOrder(orderForProduct);

        return response
                .code(HttpResponse.Status.OK)
                .body(addOrder)
                .message(HttpResponse.Status.OK.name());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('CALL_CENTER_FOR_PRODUCT','SUPER_ADMIN')")
    @Operation(summary = "This method for get ID", description = "This method get by id")
    @GetMapping("/get/{id}")
    public HttpResponse<Object> getOrderForService(@PathVariable Integer id) {

        HttpResponse<Object> response = HttpResponse.build(true);

        OrderForProductEntity orderForProduct = service.getOrderById(id);
        OrderForProductResponseDto orderForProductResponseDto = OrderForProductConvert.from(orderForProduct);

        return response
                .code(HttpResponse.Status.OK)
                .body(orderForProductResponseDto)
                .message(HttpResponse.Status.OK.name());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('CALL_CENTER_FOR_PRODUCT','SUPER_ADMIN')")
    @Operation(summary = "This method for get", description = "This method get")
    @GetMapping("/get/all")
    public HttpResponse<Object> getOrderForServiceAll() {

        HttpResponse<Object> response = HttpResponse.build(true);
        List<OrderForProductEntity> orderForProductEntityList = service.getAllOrderForService();
        List<OrderForProductResponseDto> orderForProductResponseDtoList = OrderForProductConvert.from(orderForProductEntityList);

        return response
                .code(HttpResponse.Status.OK)
                .body(orderForProductResponseDtoList)
                .message(HttpResponse.Status.OK.name());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('CALL_CENTER_FOR_PRODUCT','SUPER_ADMIN')")
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
