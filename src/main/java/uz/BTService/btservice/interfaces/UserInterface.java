package uz.BTService.btservice.interfaces;

import uz.BTService.btservice.constants.EntityStatus;

import java.time.LocalDateTime;
import java.util.Date;

public interface UserInterface {
    Integer getId();

    EntityStatus getStatus();

    Integer getCreated_by();

    LocalDateTime getCreated_date();

    Integer getModified_by();

    LocalDateTime getUpdated_date();

    Date getBirt_date();

    String getFirstname();

    String getLastname();

    String getMiddle_name();

    String getPassword();

    String getPhone_number();

    String getRole_enum_list();

    String getUsername();

    Integer getRegion_id();

    String getAddress();

    String getAttach_id();

    String getPath();

    String getType();


}
