package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.dto.TokenResponseDto;
import uz.BTService.btservice.dto.UserDto;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.entity.role.RoleEnum;
import uz.BTService.btservice.service.AuthenticationService;
import uz.BTService.btservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "This Controller manages the admins for Super_Admin")
public class AdminController {

    private final UserService userService;
    private final AuthenticationService service;


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "This method for get", description = "To get all users for admin")
    @GetMapping("/list")
    public HttpResponse<Object> getAdminList() {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {

            List<UserDto> adminList = userService.getAdminAll();
            if (adminList == null || adminList.isEmpty())
                response.code(HttpResponse.Status.NOT_FOUND).message("Not found any user!!!");
            else
                response.code(HttpResponse.Status.OK).success(true).body(adminList).message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(e.getMessage());
        }
        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "This method for post", description = "This method user register")
    @PostMapping("/add")
    public HttpResponse<Object> add(@RequestBody UserDto userDto) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            userDto.setRoleEnum(RoleEnum.ADMIN);
            response.code(HttpResponse.Status.OK).success(true).body(service.register(userDto)).message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(e.getMessage());
        }
        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "This method for get", description = "This method is used to get how many points the admin user has scored")
    @GetMapping("/info/{id}")
    public HttpResponse<Object> getAdminInformation(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            UserDto userDto = userService.getAdminInformation(id);
            response.code(HttpResponse.Status.OK).success(true).body(userDto).message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "This user for update", description = "This method is designed to delete a user by ID")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> userDelete(@PathVariable Integer id) {

        HttpResponse<Object> response = new HttpResponse<>(false);

        try {
            if (userService.userDelete(id)) {
                return response.code(HttpResponse.Status.OK).success(true).body(Boolean.TRUE).message(id + "-id admin deleted successfully");
            } else response.code(HttpResponse.Status.NOT_FOUND).success(false).message(id + " id user not found!!!");
        } catch (Exception ex) {
            ex.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(ex.getMessage());
        }
        return response;
    }

}
