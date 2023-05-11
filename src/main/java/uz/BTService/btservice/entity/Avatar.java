package uz.BTService.btservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TableNames.AVATAR)
public class Avatar extends BaseServerModifierEntity {

    private String fileOriginalName; //uz.jpa
    private long size; // 204800
    private String contentType; // application.json
    private byte[] mainContent; //Asosiy mag'zi (Content)

}
