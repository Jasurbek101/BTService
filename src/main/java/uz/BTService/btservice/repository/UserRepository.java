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
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "SELECT * FROM bts_user du WHERE du.username = :username AND status <> 'DELETED'", nativeQuery = true)
    Optional<UserEntity> findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM bts_user du WHERE du.id = :userId AND status <> 'DELETED'", nativeQuery = true)
    Optional<UserEntity> getUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM bts_user du WHERE du.username = :username OR du.phone_number = :phoneNumber", nativeQuery = true)
    Optional<UserEntity> findByUsernameOriginalDB(@Param("username") String username,@Param("phoneNumber") String phoneNumber);


    @Query(value =  "SELECT btsu.*,(\n" +
            "SELECT STRING_AGG(name, ', ')\n" +
            "    AS address FROM (\n" +
            "      WITH RECURSIVE regon_name AS(\n" +
            "         SELECT\n" +
            "             bts_child.name AS name,\n" +
            "             bts_child.id,\n" +
            "             bts_child.parent_id,\n" +
            "             0 as level\n" +
            "         FROM bts_region bts_child\n" +
            "                      WHERE bts_child.id = btsu.region_id\n" +
            "                      UNION ALL\n" +
            "         SELECT\n" +
            "             btsr_parent.name AS name,\n" +
            "             btsr_parent.id,\n" +
            "             btsr_parent.parent_id,\n" +
            "             level+1\n" +
            "         FROM bts_region btsr_parent\n" +
            "                        INNER JOIN regon_name rn on rn.parent_id = btsr_parent.id\n" +
            "                              )\n" +
            "      SELECT regon_name.name FROM regon_name\n" +
            "                        ORDER BY regon_name.level DESC )\n" +
            "        AS user_address)\n" +
            "FROM bts_user btsu WHERE btsu.status<>'DELETED'", nativeQuery = true)
    List<UserInterface> getAllUserInterface();

    @Query(value = "SELECT * FROM bts_user WHERE id = :userInformationId AND status <> 'DELETED' AND role_enum <> 'SUPER_ADMIN'", nativeQuery = true)
    UserEntity getUserInformation(@Param("userInformationId") Integer id);

    @Modifying
    @Query(value = "UPDATE bts_user SET status = 'DELETED' WHERE id = :userId AND status <> 'DELETED' AND role_enum <> 'SUPER_ADMIN'", nativeQuery = true)
    Integer userDelete(@Param("userId") Integer userId);


    @Query(value = "SELECT * FROM bts_user WHERE role_enum = 'ADMIN' AND status <> 'DELETED'", nativeQuery = true)
    List<UserEntity> getAllAdmin();

    @Query(value = "SELECT * FROM bts_user WHERE id = :adminId AND role_enum = 'ADMIN' AND status <> 'DELETED'", nativeQuery = true)
    UserEntity getAdminById(@Param("adminId") Integer adminId);
}
