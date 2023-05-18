package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.dto.CategoryDto;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.service.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
@Tag(name = "Category", description = "This Category CRUD")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "This method for post", description = "This method Category add")
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public HttpResponse<Object> addCategory(@RequestBody CategoryDto categoryDto) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(categoryService.addCategory(categoryDto))
                    .message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "This method for getId", description = "This method Category getId")
    @GetMapping("getTree/{id}")
    public HttpResponse<Object> getCategoryIdTree(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            CategoryDto category = categoryService.getCategoryIdTree(id);
            if(category ==null)  response.code(HttpResponse.Status.NOT_FOUND).success(false);
            response.code(HttpResponse.Status.OK).success(true).body(category)
                    .message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(e.getMessage());
        }
        return response;
    }
    @Operation(summary = "This method for getId", description = "This method Category getId")
    @GetMapping("get/{id}")
    public HttpResponse<Object> getCategoryId(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            CategoryDto category = categoryService.getCategoryId(id);
            if(category ==null)  response.code(HttpResponse.Status.NOT_FOUND).success(false);
            response.code(HttpResponse.Status.OK).success(true).body(category)
                    .message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(e.getMessage());
        }
        return response;
    }

    @Operation(summary = "This method for getAll", description = "This method user getAll")
    @GetMapping("getAll")
    public HttpResponse<Object> getAllCategory(){
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(categoryService.getAllCategory())
                    .message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message("Category not found!!!");
        }
        return response;
    }

    @Operation(summary = "This method for getAllId", description = "This method user getAllId")
    @GetMapping("getCategory/{length}") // nimaga kerak bu method umuman kerak masku
    public HttpResponse<Object> getAllCategory(@PathVariable Integer length){
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(categoryService.getAllIdCategory(length))
                    .message(HttpResponse.Status.OK.name());
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
                    .message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(e.getMessage());
        }
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> deleteCategory(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
                Boolean isDelete = categoryService.delete(id);
            if(isDelete){
                response.code(HttpResponse.Status.OK).success(true).body(true)
                        .message(HttpResponse.Status.OK.name());
            }else {
                response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message("Category cannot be deleted");
            }
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(HttpResponse.Status.OK.name());
        }
        return response;
    }
}
