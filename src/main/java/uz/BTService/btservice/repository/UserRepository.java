package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.interfaces.UserInterface;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM bts_user du WHERE du.username = :username AND status <> 'DELETED'", nativeQuery = true)
    Optional<UserEntity> findByUsername(@Param("username") String username);

//    Optional<UserEntity> findByUsername(String username);

    @Query(value = "SELECT * FROM bts_user WHERE status <> 'DELETED'", nativeQuery = true)
    List<UserEntity> getAllUser();


//    @Query(value =  "WITH regions AS(\n" +
//                    "    SELECT dr1.id as id,\n" +
//                    "           dr2.id as parent_id,\n" +
//                    "           dr1.name as name,\n" +
//                    "           dr2.name as parent_name\n" +
//                    "    FROM d_region dr1\n" +
//                    "    LEFT JOIN d_region dr2 ON dr1.parent_id = dr2.id\n" +
//                    "    WHERE dr1.parent_id IS NOT NULL\n" +
//                    ")\n" +
//                    "SELECT du.*,\n" +
//                    "       r.parent_ id as parent_region_id,\n" +
//                    "       r.name as region_name,\n" +
//                    "       r.parent_name as parent_region_name\n" +
//                    "FROM bts_user du\n" +
//                    "LEFT JOIN regions r ON du.region_id = ANY(ARRAY[r.id, r.parent_id])\n" +
//                    "where du.status <> 'DELETED';", nativeQuery = true)
//    List<UserInterface> getAllUserInterface();
// alt j
    @Query(value = "SELECT * FROM bts_user WHERE status <> 'DELETED'", nativeQuery = true)
    List<UserInterface> getAllUserInterface();

    @Query(value = "SELECT * FROM bts_user WHERE id = :userInformationId AND status <> 'DELETE'", nativeQuery = true)
    UserEntity getUserInformation(@Param("userInformationId") Long id);

    @Modifying
    @Query(value = "UPDATE bts_user SET status = 'DELETED' WHERE id = :userId", nativeQuery = true)
    Integer userDelete(@Param("userId") Long userId);


}
