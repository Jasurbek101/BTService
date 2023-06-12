package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.constants.OrderStatus;
import uz.BTService.btservice.entity.OrderTechnicalForServiceEntity;

import java.util.List;


public interface OrderTechnicalServiceRepository extends JpaRepository<OrderTechnicalForServiceEntity, Integer> {


    @Query(value = "SELECT * FROM bts_order_technical_service btsots WHERE btsots.id = :id AND btsots.status <> 'DELETED'", nativeQuery = true)
    OrderTechnicalForServiceEntity getOrderById(@Param("id") Integer id);

    @Query(value = "SELECT * FROM bts_order_technical_service btsots WHERE btsots.status <> 'DELETED'", nativeQuery = true)
    List<OrderTechnicalForServiceEntity> getAllOrderForServiceList();

    @Modifying
    @Query(value = "UPDATE bts_order_technical_service SET order_status = :orderStatus AND status = 'UPDATE' WHERE id = :orderId", nativeQuery = true)
    void updateOrderStatus(@Param("orderStatus") OrderStatus orderStatus, @Param("orderId") Integer orderId);
}
