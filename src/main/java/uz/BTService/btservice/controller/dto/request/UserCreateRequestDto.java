package uz.BTService.btservice.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import uz.BTService.btservice.controller.dto.base.BaseUserDto;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.entity.role.PermissionEnum;
import uz.BTService.btservice.entity.role.RoleEnum;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto extends BaseUserDto {

    @NotBlank(message = "firstname must not be null!!!")
    private String firstname;

    @NotBlank(message = "birtDate must not be null!!!")
    private String birtDate;

    @NotBlank(message = "phoneNumber must not be null!!!")
    private String phoneNumber;

    @NotBlank(message = "username must not be null!!!")
    private String username;

    @NotBlank(message = "password must not be null!!!")
    private String password;


    public UserEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new UserEntity(), ignoreProperties);
    }

}
