package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.RegionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity,Integer> {

    @Query(value = "SELECT btsr.* FROM bts_region btsr WHERE btsr.name=:regionName",nativeQuery = true)
    Optional<RegionEntity> findByCreatedByName(@Param("regionName")String regionName);

    @Query(value = "SELECT btsr.* FROM bts_region btsr WHERE btsr.id=:regionId AND btsr.status <> 'DELETED'", nativeQuery = true)
    Optional<RegionEntity> findByRegionId(@Param("regionId") Integer regionId);

    @Query(value = "SELECT btsr.* FROM bts_region btsr WHERE btsr.status <> 'DELETED'", nativeQuery = true)
    List<RegionEntity> findAllRegion();

    @Query(value = "SELECT btsr.* FROM bts_region btsr WHERE (btsr.id=:parentId OR btsr.id=:childId) AND btsr.status <> 'DELETED'", nativeQuery = true)
    List<RegionEntity> getRegionIdParentAndChild(@Param("parentId") Integer parentId, @Param("childId") Integer childId);

    @Modifying
    @Query(value = "WITH RECURSIVE sub_region AS (\n" +
            "        SELECT * FROM bts_region WHERE id = :regionId\n" +
            "        UNION ALL\n" +
            "        SELECT child.* FROM bts_region child\n" +
            "        INNER JOIN\n" +
            "        sub_region parent ON parent.id=child.parent_id\n" +
            ")UPDATE bts_region SET status = 'DELETED' WHERE id IN(SELECT id FROM sub_region)", nativeQuery = true)
    void regionDelete(@Param("regionId") Integer regionId);
}
