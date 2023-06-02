package uz.BTService.btservice.controller.convert;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.DateUtils;
import uz.BTService.btservice.common.util.DateUtil;
import uz.BTService.btservice.controller.dto.UserDto;
import uz.BTService.btservice.controller.dto.request.UserCreateRequestDto;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.entity.role.RoleEnum;
import uz.BTService.btservice.exceptions.UserDataException;

import java.text.ParseException;

@UtilityClass
public class UserConvert {

    public UserEntity convertToEntity(UserCreateRequestDto userCreateRequestDto){
        return userIgnorePropertiesAdd(userCreateRequestDto.toEntity("role", "birtDate"), userCreateRequestDto.getBirtDate(), userCreateRequestDto.getRoleEnum());
    }

    public UserEntity convertToEntity(UserDto userDto){
       return userIgnorePropertiesAdd(userDto.toEntity( "role", "birtDate"),userDto.getBirtDate(),userDto.getRoleEnum());
    }

    public UserDto from(UserEntity user){
        UserDto userDto = user.toDto("password","birtDate");
        userDto.setBirtDate(String.valueOf(DateUtil.format(user.getBirtDate(),DateUtil.PATTERN3)));
        return userDto;
    }

    private UserEntity userIgnorePropertiesAdd(UserEntity user,String birtDate, RoleEnum role) {

        try {
            user.setBirtDate(DateUtils.parseDate(birtDate, DateUtil.PATTERN3));
            user.setRoleEnum(role == RoleEnum.ADMIN ? RoleEnum.ADMIN : RoleEnum.USER);

        } catch (ParseException e) {
            e.printStackTrace();
            throw new UserDataException(e.getMessage());
        }
        return user;
    }

}
