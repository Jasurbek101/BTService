package uz.BTService.btservice.controller.dto.request;

import lombok.*;
import uz.BTService.btservice.controller.dto.base.BaseUserDto;
import uz.BTService.btservice.entity.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestDto extends BaseUserDto {

    private String firstname;

    private String birtDate;

    private String phoneNumber;

    private String username;

    private String password;

    public UserEntity toEntity(String... ignoreProperties) {
        return super.toEntity(this, new UserEntity(), ignoreProperties);
    }

}
