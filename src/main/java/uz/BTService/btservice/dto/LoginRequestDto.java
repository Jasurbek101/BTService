package uz.BTService.btservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

  @NotBlank(message = "username must not be empty")
  private String username;
  @NotBlank(message = "password must not be empty")
  String password;
}
