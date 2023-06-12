package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.entity.OrderForProductEntity;

import java.util.List;

public interface OrderForProductRepository extends JpaRepository<OrderForProductEntity, Integer> {


    @Query(value = "SELECT btsofp.* FROM bts_order_for_product btsofp WHERE btsofp.status<>'DELETED'", nativeQuery = true)
    List<OrderForProductEntity> getAllOrderProduct();


    @Query(value = "SELECT btsofp.* FROM bts_order_for_product btsofp WHERE btsofp.id = :id AND btsofp.status<>'DELETED'", nativeQuery = true)
    OrderForProductEntity getOrderForProductById(@Param("id") Integer id);


}
