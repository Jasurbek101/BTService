package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.entity.AttachmentEntity;

public interface AttachmentEntityRepository extends JpaRepository<AttachmentEntity, Long> {

    @Modifying
    @Query(value = "UPDATE bts_attachment SET status = 'DELETED' WHERE id = :attachmentId", nativeQuery = true)
    Integer attachmentDelete(@Param("attachmentId") Long attachmentId);
}
