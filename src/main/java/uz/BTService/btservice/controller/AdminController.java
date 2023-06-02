package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.controller.convert.UserConvert;
import uz.BTService.btservice.controller.dto.response.TokenResponseDto;
import uz.BTService.btservice.controller.dto.UserDto;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.request.UserCreateRequestDto;
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

            List<UserDto> adminList = userService.getAdminAll();
            if (adminList == null || adminList.isEmpty())
                response.code(HttpResponse.Status.NOT_FOUND).message("Not found any user!!!");
            else
                response.code(HttpResponse.Status.OK).success(true).body(adminList).message(HttpResponse.Status.OK.name());

        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "This method for post", description = "This method user register")
    @PostMapping("/add")
    public HttpResponse<Object> add(@RequestBody UserCreateRequestDto requestDto) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            requestDto.setRoleEnum(RoleEnum.ADMIN);
            TokenResponseDto register = service.register(UserConvert.convertToEntity(requestDto));
            response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(register)
                    .message(HttpResponse.Status.OK.name());
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

            UserDto user = UserConvert.from(userService.getAdminInformation(id));

            response
                    .code(HttpResponse.Status.OK)
                    .success(true).body(user)
                    .message(HttpResponse.Status.OK.name());

        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "This user for update", description = "This method is designed to delete a user by ID")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> userDelete(@PathVariable Integer id) {

        HttpResponse<Object> response = new HttpResponse<>(false);
        Boolean userDelete = userService.userDelete(id);

                return response.code(HttpResponse.Status.OK).success(true).body(userDelete).message(id + "-id admin deleted successfully");

    }

}
