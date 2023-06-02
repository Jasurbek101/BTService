package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.controller.convert.CategoryConvert;
import uz.BTService.btservice.controller.dto.CategoryDto;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "This Category CRUD")
public class CategoryController {

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for post", description = "This method Category add")
    @PostMapping("/add")
    public HttpResponse<Object> addCategory(@RequestBody CategoryDto categoryDto) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            CategoryEntity category = categoryService.addCategory(CategoryConvert.convertToEntity(categoryDto));
            response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(CategoryConvert.from(category))
                    .message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            response
                    .code(HttpResponse.Status.INTERNAL_SERVER_ERROR)
                    .message(e.getMessage());
        }
        return response;
    }

    private final CategoryService categoryService;

    @PreAuthorize("permitAll()")
    @Operation(summary = "This method for getId", description = "This method Category getId")
    @GetMapping("/get/tree/{id}")
    public HttpResponse<Object> getCategoryIdTree(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);

        CategoryEntity category = categoryService.getCategoryIdTree(id);
        if (category != null) {response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(CategoryConvert.fromTree(category))
                .message(HttpResponse.Status.OK.name());
        } else {
            response
                    .code(HttpResponse.Status.NOT_FOUND)
                    .success(true)
                    .message(id + " is category not found!!!");
        }

        return response;
    }

    @PreAuthorize("permitAll()")
    @Operation(summary = "This method for getId", description = "This method Category getId")
    @GetMapping("/get/{id}")
    public HttpResponse<Object> getCategoryId(@PathVariable Integer id) {

        HttpResponse<Object> response = HttpResponse.build(false);

        CategoryEntity category = categoryService.getCategoryId(id);

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(CategoryConvert.fromNoTree(category))
                .message(HttpResponse.Status.OK.name());

    }

    @PreAuthorize("permitAll()")
    @Operation(summary = "This method for getAll", description = "This method user getAll")
    @GetMapping("/get/all")
    public HttpResponse<Object> getAllCategory() {
        HttpResponse<Object> response = HttpResponse.build(false);
        List<CategoryEntity> allCategory = categoryService.getAllCategory();
        response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(CategoryConvert.fromNoTree(allCategory))
                .message(HttpResponse.Status.OK.name());


        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for Put", description = "This method user update")
    @PostMapping("/update")
    public HttpResponse<Object> update(@RequestBody CategoryDto categoryDto) {
        HttpResponse<Object> response = HttpResponse.build(false);

        CategoryEntity category = categoryService.updateCategory(categoryDto.toEntity());
        CategoryDto categoryResponseDto = CategoryConvert.fromTree(category);

        response.code(HttpResponse.Status.OK)
                .success(true)
                .body(categoryResponseDto)
                .message(HttpResponse.Status.OK.name());

        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for Delete", description = "This method user delete")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> deleteCategory(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);

        Boolean isDelete = categoryService.delete(id);

        return response.code(HttpResponse.Status.OK)
                .success(true)
                .body(isDelete)
                .message(HttpResponse.Status.OK.name());

    }
}
