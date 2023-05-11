package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.BTService.btservice.dto.ProductDto;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;
import uz.BTService.btservice.repository.ProductRepository;
import uz.BTService.btservice.service.ProductService;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "This Product CRUD")
public class ProductController extends BaseServerModifierEntity {

    private final ProductRepository productEntityRepository;
    private final ProductService productEntityService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public HttpResponse<Object> addProduct(@RequestBody ProductDto productDto, HttpServletResponse responses) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(productEntityService.addProduct(productDto, responses))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message("error");
        }
        return response;
    }
}
