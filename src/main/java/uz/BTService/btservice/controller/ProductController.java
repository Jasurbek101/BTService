package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.dto.ProductDto;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.entity.ProductEntity;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;
import uz.BTService.btservice.exceptions.ProductException;
import uz.BTService.btservice.service.ProductService;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Product", description = "This Product CRUD")
public class ProductController extends BaseServerModifierEntity {

    private final ProductService productService;

    @Operation(summary = "This method for post", description = "This method Product add")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public HttpResponse<Object> addProduct(@RequestBody ProductDto productDto, HttpServletResponse responses) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(productService.addProduct(productDto, responses))
                    .message(HttpResponse.Status.OK.name());

        } catch (Exception e) {
            response
                    .code(HttpResponse.Status.INTERNAL_SERVER_ERROR)
                    .success(false)
                    .message(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "This method for get", description = "This method get all product")
    @GetMapping("/all")
    public HttpResponse<Object> getProductAll() {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(productService.getProductAllList())
                    .message(HttpResponse.Status.OK.name());

        } catch (ProductException e) {
            response
                    .code(HttpResponse.Status.BAD_REQUEST)
                    .success(false)
                    .message(e.getMessage());
        }
        return response;
    }


    @Operation(summary = "This method for GetId", description = "This method Product GetId")
    @GetMapping("/get/{id}")
    public HttpResponse<Object> getProductNameSearch(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {

            response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(productService.getById(id))
                    .message(HttpResponse.Status.OK.name());

        } catch (Exception e) {
            response
                    .code(HttpResponse.Status.BAD_REQUEST)
                    .success(false)
                    .message(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "This method for get", description = "Product get category by id")
    @GetMapping("/category/{id}")
    public HttpResponse<Object> getProductByCategoryId(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(productService.getByCategoryId(id))
                    .message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            response
                    .code(HttpResponse.Status.BAD_REQUEST)
                    .success(false)
                    .message(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "This method for Get", description = "This method Product Search product name")
    @GetMapping("/name/{productName}")
    public HttpResponse<Object> getProductId(@PathVariable String productName) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {

            response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(productService.getProductNameSearch(productName))
                    .message(HttpResponse.Status.OK.name());

        } catch (Exception e) {
            e.printStackTrace();
            response
                    .code(HttpResponse.Status.BAD_REQUEST)
                    .success(false)
                    .message(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "This method for Delete", description = "This method Product delete")
    @PreAuthorize("hasRole('ADMIN')")
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


