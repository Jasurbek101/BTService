package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.dto.ProductDto;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.entity.Avatar;
import uz.BTService.btservice.entity.ProductEntity;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;
import uz.BTService.btservice.exceptionsAvatar.FileNotFoundException;
import uz.BTService.btservice.repository.ProductRepository;
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
            response.code(HttpResponse.Status.OK).success(true).body(productService.addProduct(productDto, responses))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message("error");
        }
        return response;
    }

    @Operation(summary = "This method for GetId", description = "This method Product GetId")
    @GetMapping("get/{id}")
    public HttpResponse<Object> getProductId(@PathVariable Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            ProductEntity product = productService.getProduct(id);
            System.out.println("test->>>>>>>>>>>>>>>>>>>>>> "+ product);
            response.code(HttpResponse.Status.OK).success(true).body(product)
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message(id + " not found!!!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

//    @Operation(summary = "This method for GetAll", description = "This method Product getAll")
//    @GetMapping("getAll")
//    public HttpResponse<Object> getProductId() {
//        HttpResponse<Object> response = HttpResponse.build(false);
//        try {
//            response.code(HttpResponse.Status.OK).success(true).body(productService.getAllProduct())
//                    .message("successfully!!!");
//        } catch (Exception e) {
//            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message("Error!");
//        }
//        return response;
//    }

    @Operation(summary = "This method for Put", description = "This method Product update")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public HttpResponse<Object> update(@RequestBody ProductDto productDto,@PathVariable Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(productService.updateProduct(productDto,id))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(productDto.getName() + " error");
        }
        return response;
    }


    @Operation(summary = "This method for Delete", description = "This method Product delete")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> deleteProductId(@PathVariable Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(productService.delete(id))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(id + " not found!!!");
        }
        return response;
    }
}


