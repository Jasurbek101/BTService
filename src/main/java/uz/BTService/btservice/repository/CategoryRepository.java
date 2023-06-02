package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    @Query(value = "SELECT btsc.* FROM bts_category btsc WHERE btsc.id=:categoryId AND btsc.status <> 'DELETED'", nativeQuery = true)
    Optional<CategoryEntity> findByCategoryId(@Param("categoryId") Integer categoryId);

    @Query(value = "SELECT btsc.* FROM bts_category btsc WHERE btsc.status <> 'DELETED'", nativeQuery = true)
    List<CategoryEntity> findAllCategory();


    @Modifying
    @Query(value = "WITH RECURSIVE sub_category AS (\n" +
            "        SELECT * FROM bts_category WHERE id = :categoryId\n" +
            "        UNION ALL\n" +
            "        SELECT child.* FROM bts_category child\n" +
            "        INNER JOIN\n" +
            "        sub_category parent ON parent.id=child.parent_id\n" +
            ")UPDATE bts_category SET status = 'DELETED' WHERE id IN(SELECT id FROM sub_category)", nativeQuery = true)
    void categoryDelete(@Param("categoryId") Integer categoryId);

    @Query(value = "SELECT btsc.* FROM bts_category btsc WHERE btsc.name=:categoryName",nativeQuery = true)
    Optional<CategoryEntity> findByCreatedByName(@Param("categoryName")String categoryName);

    @Query(value = "SELECT btsc.* FROM bts_category btsc WHERE (btsc.id=:parentId OR btsc.id=:childId) AND btsc.status <> 'DELETED'", nativeQuery = true)
    List<CategoryEntity> getCategoryIdParentAndChild(@Param("parentId") Integer parentId, @Param("childId") Integer childId);
}
