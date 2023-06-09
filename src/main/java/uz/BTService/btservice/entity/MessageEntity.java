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

    @Column(name = "order_servicer_id")
    private Integer orderServiceId;

    @ManyToOne
    @JoinColumn(name = "order_servicer_id", insertable = false, updatable = false)
    private OrderTechnicalServiceEntity orderTechnicalService;


}
