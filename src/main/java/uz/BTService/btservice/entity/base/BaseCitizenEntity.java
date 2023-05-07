package uz.BTService.btservice.entity.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseCitizenEntity extends BaseServerModifierEntity{
    private String firstName;

    private String lastName;

    private String middleName;

    private String phoneNumber;
}
