package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.entity.AttachmentContentEntity;
import uz.BTService.btservice.entity.AttachmentEntity;

import java.util.Optional;

public interface AttachmentContentEntityRepository extends JpaRepository<AttachmentContentEntity, Long> {


    @Query(value = "SELECT btsa.* FROM bts_attachment_content btsa WHERE btsa.id=:attachmentEntityId AND btsa.status <> 'DELETED'", nativeQuery = true)
    Optional<AttachmentContentEntity> findByAttachmentEntityId(@Param("attachmentEntityId") Long attachmentEntityId);

    @Modifying
    @Query(value = "UPDATE bts_attachment_content SET status = 'DELETED' WHERE id = :attachmentId", nativeQuery = true)
    Integer attachmentDelete(@Param("attachmentId") Long attachmentId);
}
