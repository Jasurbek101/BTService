package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.entity.AttachmentContentEntity;
import uz.BTService.btservice.entity.AttachmentEntity;

import java.util.Optional;

public interface AttachmentEntityRepository extends JpaRepository<AttachmentEntity, Long> {

    @Query(value = "SELECT btsa.* FROM bts_attachment btsa WHERE btsa.id=:attachmentEntityId AND btsa.status <> 'DELETED'", nativeQuery = true)
    Optional<AttachmentEntity> findById(@Param("attachmentEntityId") Long attachmentEntityId);

    @Modifying
    @Query(value = "UPDATE bts_attachment SET status = 'DELETED' WHERE id = :attachmentId", nativeQuery = true)
    Integer attachmentDelete(@Param("attachmentId") Long attachmentId);
}
