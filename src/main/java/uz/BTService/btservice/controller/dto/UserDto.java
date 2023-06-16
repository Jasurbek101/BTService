package uz.BTService.btservice.controller.dto;

import lombok.*;
import uz.BTService.btservice.controller.dto.base.BaseServerModifierDto;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.entity.role.RoleEnum;

import java.util.List;

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

    private String address;

    private List<RoleEnum> roleEnumList;
    public UserEntity toEntity( String... ignoreProperties) {
        return super.toEntity(this, new UserEntity(), ignoreProperties);
    }

}
