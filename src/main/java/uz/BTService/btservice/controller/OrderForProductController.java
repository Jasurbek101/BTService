package uz.BTService.btservice.controller;

import lombok.RequiredArgsConstructor;
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
