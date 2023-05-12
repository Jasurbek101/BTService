package uz.BTService.btservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.BTService.btservice.constants.TableNames;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = TableNames.ATTACHMENT_ENTITY)
public class AttachmentEntity extends BaseServerModifierEntity {

    private String fileOriginalName; //uz.jpa
    private long size; // 204800
    private String contentType; // application.json


}
