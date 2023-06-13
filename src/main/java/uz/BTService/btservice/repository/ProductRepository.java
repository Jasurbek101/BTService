package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.entity.ProductEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query(value = "SELECT p FROM ProductEntity p INNER JOIN CategoryEntity c ON p.id=:productId AND c.id=p.category AND p.status <> 'DELETED' AND c.status <> 'DELETED'")
    Optional<ProductEntity> findByProductId(@Param("productId") Integer productId);

    @Query(value = "SELECT btsp.* FROM bts_product btsp WHERE btsp.id=:productId", nativeQuery = true)
    Optional<ProductEntity> findByProductIdOrgDB(@Param("productId") Integer productId);

    @Query(value = "SELECT bp.*\n" +
            "FROM bts_product bp\n" +
            "LEFT JOIN bts_category bc on bp.category_id = bc.id\n" +
            "WHERE bp.status <> 'DELETED' AND bc.status <> 'DELETED'", nativeQuery = true)
    List<ProductEntity> getAllProduct();


    @Query(value = "SELECT btsp.* FROM bts_product btsp", nativeQuery = true)
    List<ProductEntity> getAll();

    @Query(value = "SELECT btsp.* FROM bts_product btsp " +
            "INNER JOIN bts_category btsc ON btsp.category_id = :categoryId " +
            "AND btsc.id=btsp.category_id " +
            "AND btsp.status <> 'DELETED' " +
            "AND btsc.status <> 'DELETED'", nativeQuery = true)
    List<ProductEntity> getCategoryId(@Param("categoryId") Integer categoryId);


    @Query(value = "SELECT btsp.* FROM bts_product btsp\n" +
            "            INNER JOIN bts_category btsc ON btsp.category_id = btsc.id\n" +
            "                WHERE btsp.name ILIKE ANY (ARRAY [:productName])\n" +
            "                   AND btsp.status<>'DELETED'\n" +
            "                   AND btsc.status<>'DELETED'\n" +
            "                 ORDER BY CASE WHEN\n" +
            "                  btsp.name = ANY (ARRAY [:productName])\n" +
            "                   THEN 0 ELSE 1 END, btsp.name", nativeQuery = true)
    List<ProductEntity> getProductNameListSearch(@Param("productName") String[] categoryNameList);

    @Modifying
    @Query(value = "UPDATE bts_product SET status = 'DELETED' AND updated_date = now() AND modified_by = :userId WHERE id = :productId", nativeQuery = true)
    Integer productDeleted(@Param("productId") Integer productId, @Param("userId")Integer userId);


    @Query(value = "SELECT btsp.* FROM bts_product btsp " +
            "WHERE btsp.status = 'DELETED'" +
            " AND btsp.updated_date BETWEEN " +
            "COALESCE(:startDate, CAST('1970-01-01 00:00:00' AS TIMESTAMP WITHOUT TIME ZONE)) " +
            "AND COALESCE(:endDate, NOW())",nativeQuery = true)
    List<ProductEntity> getDeletedProductByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
