package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

    @Query(value = "SELECT p FROM ProductEntity p INNER JOIN CategoryEntity c ON p.id=:productId AND c.id=p.category AND p.status <> 'DELETED' AND c.status <> 'DELETED'")
    Optional<ProductEntity> findByProductId(@Param("productId") Integer productId);

    @Query(value = "SELECT btsp.* FROM bts_product btsp WHERE btsp.id=:productId", nativeQuery = true)
    Optional<ProductEntity> findByProductIdOrgDB(@Param("productId") Integer productId);

    @Query(value = "SELECT p FROM ProductEntity p INNER JOIN CategoryEntity c ON c.id=p.category AND p.status <> 'DELETED' AND c.status <> 'DELETED'")
    List<ProductEntity> getAllProduct();

    List<ProductEntity> findAllByStatusNot(EntityStatus status);


    @Query(value = "SELECT btsp.* FROM bts_product btsp", nativeQuery = true)
    List<ProductEntity> getAll();

    @Query(value = "SELECT p FROM ProductEntity p INNER JOIN CategoryEntity c ON p.categoryId = :categoryId AND c.id=p.category AND p.status <> 'DELETED' AND c.status <> 'DELETED'")
    List<ProductEntity> getCategoryId(@Param("categoryId") Integer categoryId);


    @Modifying
    @Query(value = "UPDATE bts_product SET status = 'DELETED' WHERE id = :productId", nativeQuery = true)
    Integer productDeleted(@Param("productId") Integer productId);

}
