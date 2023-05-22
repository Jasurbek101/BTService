package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

    @Query(value = "SELECT p FROM ProductEntity p INNER JOIN CategoryEntity c ON p.id=:productId AND c.id=p.category AND p.status <> 'DELETED' AND c.status <> 'DELETED'")
    Optional<ProductEntity> findByProductId(@Param("productId") Integer productId);

    @Query(value = "SELECT btsp.* FROM bts_product btsp WHERE btsp.id=:productId", nativeQuery = true)
    Optional<ProductEntity> findByProductIdOrgDB(@Param("productId") Integer productId);

    @Query(value = "SELECT bp.*\n" +
            "FROM bts_product bp\n" +
            "LEFT JOIN bts_category bc on bp.category_id = bc.id\n" +
            "WHERE bp.status <> 'DELETED'", nativeQuery = true)
    List<ProductEntity> getAllProduct();

    List<ProductEntity> findAllByStatusNot(EntityStatus status);


    @Query(value = "SELECT btsp.* FROM bts_product btsp", nativeQuery = true)
    List<ProductEntity> getAll();

    @Query(value = "SELECT p FROM ProductEntity p INNER JOIN CategoryEntity c ON p.categoryId = :categoryId AND c.id=p.category AND p.status <> 'DELETED' AND c.status <> 'DELETED'")
    List<ProductEntity> getCategoryId(@Param("categoryId") Integer categoryId);


    @Query(value = "SELECT btsp.* FROM bts_product btsp\n" +
            "INNER JOIN bts_category btsc ON btsp.category_id = btsc.id\n" +
            "     WHERE btsp.name ILIKE ANY (ARRAY [:categoryNameList])\n" +
            "        AND btsp.status<>'DELETED'AND btsc.status<>'DELETED'\n" +
            "     ORDER BY CASE WHEN\n" +
            "      btsp.name = ANY (ARRAY [:categoryNameList])\n" +
            "       THEN 0 ELSE 1 END,\n" +
            "btsp.name DESC", nativeQuery = true)
    List<ProductEntity> getProductNameListSearch(@Param("categoryNameList") String[] categoryNameList);

    @Modifying
    @Query(value = "UPDATE bts_product SET status = 'DELETED' WHERE id = :productId", nativeQuery = true)
    Integer productDeleted(@Param("productId") Integer productId);
}
