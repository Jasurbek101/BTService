package uz.BTService.btservice.entity;

import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.dto.UserDto;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;
import uz.BTService.btservice.entity.role.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.DEPARTMENT_USER)
public class UserEntity extends BaseServerModifierEntity implements UserDetails {

  private String firstname;
  private String lastname;
  private String middleName;
  private Date birtDate;
  @Column(unique = true,nullable = false)
  private String phoneNumber;
  @Column(unique = true,nullable = false)
  private String username;
  private String password;

  @Enumerated(EnumType.STRING)
  private RoleEnum roleEnum;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(roleEnum.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }


    /************************************************************
    * ******************** CONVERT TO DTO ***********************
    * ***********************************************************/
    public UserDto toDto(String... ignoreProperties){
      return toDto(this, new UserDto(), ignoreProperties);
    }
}
