package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.dto.ProductDto;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.entity.Avatar;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;
import uz.BTService.btservice.repository.ProductRepository;
import uz.BTService.btservice.service.ProductService;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "This Product CRUD")
public class ProductController extends BaseServerModifierEntity {

    private final ProductRepository productEntityRepository;
    private final ProductService productService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public HttpResponse<Object> addProduct(@RequestBody ProductDto productDto, HttpServletResponse responses) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(productService.addProduct(productDto, responses))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message("error");
        }
        return response;
    }

    @GetMapping("get/{id}")
    public HttpResponse<Object> getProductId(@PathVariable Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            Avatar avatar = productService.getProduct(id).getAvatar();
            response.code(HttpResponse.Status.OK).success(true).body(productService.getProduct(id).getAvatar().getMainContent())
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message(id + " not found!!!");
        }
        return response;
    }

    @GetMapping("getAll")
    public HttpResponse<Object> getProductId() {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(productService.getAllProduct().get(0).getName())
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message("Error!");
        }
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public HttpResponse<Object> update(@RequestBody ProductDto productDto) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(productService.updateProduct(productDto))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(productDto.getName() + " error");
        }
        return response;
    }


}


