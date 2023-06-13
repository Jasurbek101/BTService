package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.controller.convert.ProductConvert;
import uz.BTService.btservice.controller.dto.dtoUtil.FilterForm;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.request.ProductCreateRequestDto;
import uz.BTService.btservice.controller.dto.response.ProductResponseForAdminDto;
import uz.BTService.btservice.entity.ProductEntity;
import uz.BTService.btservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "This Product CRUD")
public class ProductControllerForAdmin {

    private final ProductService productService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for post", description = "This method Product add")
    @PostMapping("/add")
    public HttpResponse<Object> addProduct(@RequestBody ProductCreateRequestDto productDto) {

        HttpResponse<Object> response = HttpResponse.build(false);

        ProductEntity product = ProductConvert.convertToEntity(productDto);
        boolean isSave = productService.addProduct(product,productDto.getCategoryId());

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(isSave)
                .message(HttpResponse.Status.OK.name());

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for GetId", description = "This method Product GetId")
    @GetMapping("/for-admin/get/{id}")
    public HttpResponse<Object> getProductIdForAdmin(@PathVariable Integer id) {

        HttpResponse<Object> response = HttpResponse.build(false);

        ProductEntity responseProduct = productService.getById(id);
        ProductResponseForAdminDto responseForAdminDto = ProductConvert.fromForAdmin(responseProduct);

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(responseForAdminDto)
                .message(HttpResponse.Status.OK.name());

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for get", description = "This method get all product")
    @GetMapping("/for-admin/get/all")
    public HttpResponse<Object> getProductAllForAdmin() {HttpResponse<Object> response = HttpResponse.build(false);

        List<ProductEntity> productAllList = productService.getProductAllList();
        List<ProductResponseForAdminDto> productResponseForAdminDtoList = ProductConvert.fromForAdmin(productAllList);

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(productResponseForAdminDtoList)
                .message(HttpResponse.Status.OK.name());

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for get", description = "Product get category by id")
    @GetMapping("/for-admin/category/get/{id}")
    public HttpResponse<Object> getProductByCategoryIdForAdmin(@PathVariable Integer id) {

        HttpResponse<Object> response = HttpResponse.build(false);

        List<ProductEntity> responseEntityList = productService.getByCategoryId(id);
        List<ProductResponseForAdminDto> productResponseForAdminDtoList = ProductConvert.fromForAdmin(responseEntityList);

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(productResponseForAdminDtoList)
                .message(HttpResponse.Status.OK.name());

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for get", description = "Products deleted during the period")
    @PostMapping("/for-admin/deleted-product")
    public HttpResponse<Object> getProductByCategoryIdForAdmin(@RequestBody FilterForm filter) {

        HttpResponse<Object> response = HttpResponse.build(false);

        List<ProductEntity> responseEntityList = productService.getDeletedProductsByDate(filter);
        List<ProductResponseForAdminDto> productResponseForAdminDtoList = ProductConvert.fromForAdmin(responseEntityList);

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(productResponseForAdminDtoList)
                .message(HttpResponse.Status.OK.name());

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for Delete", description = "This method Product delete")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> deleteProductId(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(productService.delete(id))
                    .message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            response
                    .code(HttpResponse.Status.INTERNAL_SERVER_ERROR)
                    .success(false)
                    .message(e.getMessage());
        }
        return response;
    }

}
