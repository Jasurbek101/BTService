package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.BTService.btservice.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

}
