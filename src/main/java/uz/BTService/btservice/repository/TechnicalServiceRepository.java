package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.entity.TechnicalServiceEntity;

import java.util.List;

public interface TechnicalServiceRepository extends JpaRepository<TechnicalServiceEntity, Integer> {

    @Query(value = "SELECT btsts.* FROM bts_technical_service btsts WHERE btsts.id=:technicalServiceId AND btsts.status<>'DELETED'",nativeQuery = true)
    TechnicalServiceEntity getByTechnicalId(@Param("technicalServiceId") Integer technicalServiceId);

    @Query(value = "SELECT btsts.* FROM bts_technical_service btsts" +
            " INNER JOIN bts_category btsc ON" +
            " btsts.category_id =:categoryId" +
            " AND btsts.category_id = btsc.id" +
            " AND btsts.status <> 'DELETED'" +
            " AND btsc.status <> 'DELETED'", nativeQuery = true)
    List<TechnicalServiceEntity> getTechnicalServiceCategoryType(@Param("categoryId") Integer categoryId);

    @Modifying
    @Query(value = "UPDATE bts_technical_service SET status = 'DELETED' WHERE id = :id", nativeQuery = true)
    void technicalServiceDelete(@Param("id") Integer id);
}
