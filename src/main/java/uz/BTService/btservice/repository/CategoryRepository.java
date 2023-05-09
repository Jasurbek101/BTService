package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.entity.CategoryEntity;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    @Query(value = "SELECT btsc.* FROM bts_category btsc WHERE btsc.id=:categoryId AND btsc.status <> 'DELETED'", nativeQuery = true)
    Optional<CategoryEntity> findByCategoryId(@Param("categoryId") Long categoryId);

    @Modifying
    @Query(value = "UPDATE bts_category SET status = 'DELETED' WHERE id = :categoryId", nativeQuery = true)
    Integer categoryDelete(@Param("categoryId") Long categoryId);
}
