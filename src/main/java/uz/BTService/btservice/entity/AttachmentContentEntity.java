package uz.BTService.btservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = TableNames.ATTACHMENT_CONTENT_ENTITY)
public class AttachmentContentEntity extends BaseServerModifierEntity {

    private byte[] mainContent; //Asosiy mag'zi (Content)
    @OneToOne
    private AttachmentEntity attachmentEntity; // qaysi fayilga tegishli ekanligi
}

