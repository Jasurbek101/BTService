package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.entity.Avatar;

import java.util.Optional;

public interface AvatarRepository  extends JpaRepository<Avatar,Long> {
    @Query(value = "SELECT btsa.* FROM bts_avatar btsa WHERE btsa.id=:attachmentEntityId AND btsa.status <> 'DELETED'", nativeQuery = true)
    Optional<Avatar> findById(@Param("attachmentEntityId") Long attachmentEntityId);

    @Modifying
    @Query(value = "UPDATE bts_avatar SET status = 'DELETED' WHERE id = :attachmentId", nativeQuery = true)
    Integer avatarDelete(@Param("attachmentId") Long attachmentId);

    @Query(value = "SELECT btsa.* FROM bts_avatar btsa WHERE btsa.id=:attachmentEntityId AND btsa.status <> 'DELETED'", nativeQuery = true)
    Avatar findByNotOptionalId(@Param("attachmentEntityId") Long attachmentEntityId);
}
