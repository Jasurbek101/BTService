package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.dto.CategoryDto;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.service.CategoryService;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "This Category CRUD")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "This method for post", description = "This method Category add")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public HttpResponse<Object> addCategory(@RequestBody CategoryDto categoryDto) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(categoryService.addCategory(categoryDto))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message(categoryDto.getName() + " bor");
        }
        return response;
    }

    @Operation(summary = "This method for getId", description = "This method Category getId")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get/{id}")
    public HttpResponse<Object> getCategoryId(@PathVariable Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(categoryService.getCategoryId(id))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(id + " not found!!!");
        }
        return response;
    }

    @Operation(summary = "This method for getAll", description = "This method user getAll")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getAll")
    public HttpResponse<Object> getAllCategory(){
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(categoryService.getAllCategory())
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message("Category not found!!!");
        }
        return response;
    }

    @Operation(summary = "This method for getAllId", description = "This method user getAllId")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("getAllId/{id}")
    public HttpResponse<Object> getAllCategory(@PathVariable Long id){
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(categoryService.getAllIdCategory(id))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message("Category not found!!!");
        }
        return response;
    }

    @Operation(summary = "This method for Put", description = "This method user update")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/update")
    public HttpResponse<Object> update(@RequestBody CategoryDto categoryDto) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(categoryService.updateCategory(categoryDto))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(categoryDto.getName() + " error");
        }
        return response;
    }

    @Operation(summary = "This method for Delete", description = "This method Category delete")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> deleteCategory(@PathVariable Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            Boolean delete = categoryService.delete(id);
            if (!delete)
                throw new FileNotFoundException("file not found");

                response.code(HttpResponse.Status.OK).success(true).body(delete)
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(id + " not found!!!");
        }
        return response;
    }
}
