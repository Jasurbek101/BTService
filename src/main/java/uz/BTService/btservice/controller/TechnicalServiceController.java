package uz.BTService.btservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.controller.convert.TechnicalServiceConvert;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.request.TechnicalServiceCreate;
import uz.BTService.btservice.controller.dto.response.TechnicalServiceResponseDto;
import uz.BTService.btservice.entity.TechnicalServiceEntity;
import uz.BTService.btservice.service.TechnicalService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/technical-service")
@RequiredArgsConstructor
public class TechnicalServiceController {

    private final TechnicalService service;

    @PostMapping("/add")
    public HttpResponse<Object> add(@RequestBody TechnicalServiceCreate technicalServiceCreate){

        HttpResponse<Object> response = new HttpResponse<>(true);

        TechnicalServiceEntity technicalServiceEntity = TechnicalServiceConvert.convertToEntity(technicalServiceCreate);
        boolean add = service.add(technicalServiceEntity);

        return response
                .message(HttpStatus.OK.name())
                .code(HttpResponse.Status.OK)
                .body(add);

    }

    @GetMapping("/get/{id}")
    public HttpResponse<Object> getById(@PathVariable Integer id){

        HttpResponse<Object> response = new HttpResponse<>(true);

        TechnicalServiceEntity getById = service.getById(id);
        TechnicalServiceResponseDto serviceResponseDto = TechnicalServiceConvert.from(getById);

        return response
                .message(HttpStatus.OK.name())
                .code(HttpResponse.Status.OK)
                .body(serviceResponseDto);
    }

    @GetMapping("/get/{categoryId}")
    public HttpResponse<Object> getCategoryId(@PathVariable Integer categoryId){
        HttpResponse<Object> response = new HttpResponse<>(true);

        List<TechnicalServiceEntity> getById = service.getTechnicalServiceCategoryType(categoryId);
        List<TechnicalServiceResponseDto> serviceResponseDto = TechnicalServiceConvert.from(getById);

        return response
                .message(HttpStatus.OK.name())
                .code(HttpResponse.Status.OK)
                .body(serviceResponseDto);
    }

    @DeleteMapping("/deleted/{id}")
    public HttpResponse<Object> getDeletedById(@PathVariable Integer id){
        HttpResponse<Object> response = new HttpResponse<>(true);

        service.deletedById(id);

        return response
                .message(HttpStatus.OK.name())
                .code(HttpResponse.Status.OK)
                .body(true);
    }
}
