package uz.BTService.btservice.entity.role;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RolePermissionEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleEnum;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> permissionEnum;

    public RolePermissionEntity(List<String> roleEnum, List<String> permissionEnum) {
        this.roleEnum = roleEnum;
        this.permissionEnum = permissionEnum;
    }

    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        if (roleEnum!=null) {
            for (String role : roleEnum) {
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }
        if (permissionEnum != null) {
            for (String permission : permissionEnum) {
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(permission));
            }
        }
            return simpleGrantedAuthorities;
    }
}
