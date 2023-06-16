package uz.BTService.btservice.controller.dto.request;

import lombok.Getter;
import lombok.Setter;
import uz.BTService.btservice.entity.role.RoleEnum;

import java.util.List;


@Getter
@Setter
public class AdminCreateRequestDto extends UserCreateRequestDto{

    private List<RoleEnum> roles;
}
