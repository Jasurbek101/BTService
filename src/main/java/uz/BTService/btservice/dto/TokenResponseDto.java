package uz.BTService.btservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.BTService.btservice.entity.role.RoleEnum;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {

  private String token;
  private UserDto user;
}
