package uz.BTService.btservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String text;

    @Column(name = "order_service_id")
    private Integer orderServiceId;

    @ManyToOne
    @JoinColumn(name = "order_service_id", insertable = false, updatable = false)
    private OrderTechnicalForServiceEntity orderTechnicalService;

    @Column(name = "order_product_id")
    private Integer orderForProductId;

    @ManyToOne
    @JoinColumn(name = "order_product_id", insertable = false, updatable = false)
    private OrderForProductEntity orderForProductEntity;

}
