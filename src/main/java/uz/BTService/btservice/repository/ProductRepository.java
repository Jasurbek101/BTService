package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    @Query(value = "SELECT btsp.* FROM bts_product btsp WHERE btsp.id=:productId AND btsp.status <> 'DELETED'", nativeQuery = true)
    Optional<ProductEntity> findByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT btsp.* FROM bts_product btsp WHERE btsp.STATUS <> 'DELETED'", nativeQuery = true)
    List<ProductEntity> findByAllProduct();
}
