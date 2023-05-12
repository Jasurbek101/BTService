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
    private Long size; // 204800
    private String contentType; // application.json, image/png
    private byte[] mainContent; //Asosiy mag'zi (Content)
    private String name; // PAPKANI ICHIDAN TOPISH UCHUN //BU FILENI SYSTEMAGA SAQLAGANDA KERAK BULADI

}
