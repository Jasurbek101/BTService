package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.controller.convert.ProductConvert;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.request.ProductCreateRequestDto;
import uz.BTService.btservice.controller.dto.response.ProductResponseForAdminDto;
import uz.BTService.btservice.controller.dto.response.ProductResponseForUserDto;
import uz.BTService.btservice.entity.ProductEntity;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;
import uz.BTService.btservice.exceptions.ProductException;
import uz.BTService.btservice.service.ProductService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "This Product CRUD")
public class ProductController extends BaseServerModifierEntity {

    private final ProductService productService;


    @Operation(summary = "This method for GetId", description = "This method Product GetId")
    @GetMapping("/get/{id}")
    public HttpResponse<Object> getProductId(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);

        ProductEntity responseProduct = productService.getById(id);
        ProductResponseForUserDto responseForUserDto = ProductConvert.from(responseProduct);

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(responseForUserDto)
                .message(HttpResponse.Status.OK.name());

    }



    @Operation(summary = "This method for get", description = "This method get all product")
    @GetMapping("/get/all")
    public HttpResponse<Object> getProductAll() {

        HttpResponse<Object> response = HttpResponse.build(false);

        List<ProductEntity> productAllList = productService.getProductAllList();
        List<ProductResponseForUserDto> responseForUserDtoList = ProductConvert.from(productAllList);

            response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(responseForUserDtoList)
                    .message(HttpResponse.Status.OK.name());


        return response;
    }


    @Operation(summary = "This method for get", description = "Product get category by id")
    @GetMapping("/get/category/{id}")
    public HttpResponse<Object> getProductByCategoryId(@PathVariable Integer id) {

        HttpResponse<Object> response = HttpResponse.build(false);

        List<ProductEntity> responseEntityList = productService.getByCategoryId(id);
        List<ProductResponseForUserDto> responseForUserDtoList = ProductConvert.from(responseEntityList);

        return response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(responseForUserDtoList)
                    .message(HttpResponse.Status.OK.name());

    }



    @Operation(summary = "This method for Get", description = "This method Product Search product name")
    @GetMapping("/get/name/{productName}")
    public HttpResponse<Object> getProductNameSearch(@PathVariable String productName) {
        HttpResponse<Object> response = HttpResponse.build(false);

        List<ProductEntity> responseEntityList = productService.getProductNameSearch(productName);
        List<ProductResponseForUserDto> responseProductList = ProductConvert.from(responseEntityList);

        return response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(responseProductList)
                    .message(HttpResponse.Status.OK.name());

    }

}


