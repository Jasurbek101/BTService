package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.BTService.btservice.dto.LoginRequestDto;
import uz.BTService.btservice.dto.TokenResponseDto;
import uz.BTService.btservice.dto.UserDto;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.entity.role.RoleEnum;
import uz.BTService.btservice.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "This Controller for login and register")
public class AuthenticationController {

    private final AuthenticationService service;


    @Operation(summary = "This method for post", description = "This method user register")
    @PostMapping("/register")
    public HttpResponse<Object> register(@RequestBody UserDto userDto) {
        HttpResponse<Object> response = HttpResponse.build(false);
        System.out.println("hallo");
        try {
            userDto.setRoleEnum(RoleEnum.USER);
            response.code(HttpResponse.Status.OK).success(true).body(service.register(userDto)).message("successfully!!!");
        }catch (RuntimeException e){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(userDto.toString()+" error in the data sent");
        }
        return response;
    }

    @Operation(summary = "This method for post", description = "This method user login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
