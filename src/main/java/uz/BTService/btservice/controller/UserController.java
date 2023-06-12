package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.controller.convert.UserConvert;
import uz.BTService.btservice.controller.dto.UserDto;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.request.UserUpdateRequestDto;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.interfaces.UserInterface;
import uz.BTService.btservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "This Controller manages the user for Admin")
public class UserController {

    private final UserService userService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @Operation(summary = "This method for get", description = "To get all users for admin")
    @GetMapping("/list")
    public HttpResponse<Object> getUserList() {
        HttpResponse<Object> response = HttpResponse.build(true);

        List<UserInterface> userAll = userService.getUserAll();
        List<UserDto> userDtoList = UserConvert.from(userAll);

        return response
                .code(HttpResponse.Status.OK)
                .body(userDtoList)
                .message(HttpResponse.Status.OK.name());

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for get", description = "This method is used to get how many points the admin user has scored")
    @GetMapping(value = "/info/{id}")
    public HttpResponse<Object> getUserInformation(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(true);

        UserEntity userEntity = userService.getUserInformation(id);
        UserDto userDto = UserConvert.from(userEntity);

        return response
                .code(HttpResponse.Status.OK)
                .body(userDto)
                .message(HttpResponse.Status.OK.name());

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "This method for update", description = "This method updates the user's data")
    @PostMapping("/update")
    public HttpResponse<Object> userUpdate(@RequestBody UserUpdateRequestDto userUpdate) {
        HttpResponse<Object> response = new HttpResponse<>(true);

        UserEntity updateUser = userUpdate.toEntity();
        Boolean isUpdateUser = userService.updateUser(updateUser);

        return response
                .code(HttpResponse.Status.OK)
                .body(isUpdateUser)
                .message(HttpStatus.OK.name());

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "This method for update by id", description = "This method updates the user's data")
    @PutMapping("/update/{id}")
    public HttpResponse<Object> userUpdateId(
            @PathVariable Integer id,
            @RequestBody UserUpdateRequestDto userUpdate) {

        HttpResponse<Object> response = new HttpResponse<>(true);

        UserEntity updateUser = userUpdate.toEntity();
        Boolean isUpdateUser = userService.updateUserById(updateUser, id);

        return response
                .code(HttpResponse.Status.OK)
                .body(isUpdateUser)
                .message(HttpStatus.OK.name());

    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "This user for delete", description = "This method is designed to delete a user by ID")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> userDelete(@PathVariable Integer id) {

        HttpResponse<Object> response = new HttpResponse<>(false);


            if (Boolean.TRUE.equals(userService.userDelete(id))) {
                return response
                        .code(HttpResponse.Status.OK)
                        .success(true)
                        .body(Boolean.TRUE)
                        .message("User deleted successfully");
            }

        return response;
    }


}
