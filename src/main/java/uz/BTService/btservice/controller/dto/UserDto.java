package uz.BTService.btservice.controller.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.BTService.btservice.controller.dto.base.BaseServerModifierDto;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.entity.role.RoleEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseServerModifierDto {
    private String firstname;
    private String lastname;
    private String middleName;
    private String birtDate;
    private String phoneNumber;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    public UserEntity toEntity( String... ignoreProperties) {
        return super.toEntity(this, new UserEntity(), ignoreProperties);
    }

}
