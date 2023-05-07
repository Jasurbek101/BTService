package uz.BTService.btservice.interfaces;

import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.entity.role.RoleEnum;

import java.util.Date;

public interface UserInterface {
    Long getId();

    EntityStatus getStatus();

    Long getCreated_by();

    Date getCreated_date();

    Long getModified_by();

    Date getUpdated_date();

    Date getBirth_date();

    String getFirstname();

    String getLastname();

    String getMiddle_name();

    String getPassword();

    String getPhone_number();

    RoleEnum getRole();

    String getUsername();

    Long getImage_id();

    Long getNeighborhood_id();

    Long getRegion_id();

    Long getParent_region_id();

    String getRegion_name();

    String getParent_region_name();
}
