package uz.BTService.btservice.controller.convert;

import lombok.experimental.UtilityClass;
import uz.BTService.btservice.controller.dto.response.TokenResponseDto;
import uz.BTService.btservice.entity.UserEntity;

@UtilityClass
public class TokenResponseConvert {

    public TokenResponseDto from(String token, UserEntity user){
        return TokenResponseDto.builder()
                .token(token)
                .user(UserConvert.from(user))
                .build();
    }


}
