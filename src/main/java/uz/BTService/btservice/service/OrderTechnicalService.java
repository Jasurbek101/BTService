package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.constants.MassageText;
import uz.BTService.btservice.constants.OrderStatus;
import uz.BTService.btservice.entity.MessageEntity;
import uz.BTService.btservice.entity.OrderTechnicalForServiceEntity;
import uz.BTService.btservice.repository.MassageRepository;
import uz.BTService.btservice.repository.OrderTechnicalServiceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderTechnicalService {

    private final OrderTechnicalServiceRepository repository;
    private final MassageRepository massageRepository;

    public boolean addOrder(OrderTechnicalForServiceEntity orderTechnicalService) {

        MessageEntity message = new MessageEntity();

        message.setOrderServiceId(orderTechnicalService.getTechnicalServiceId());
        message.setText(MassageText.ORDER_SERVICE_CREATE);

        orderTechnicalService.setOrderStatus(OrderStatus.NEW);
        repository.save(orderTechnicalService);
        massageRepository.save(message);

        return true;

    }

    public OrderTechnicalForServiceEntity getOrderById(Integer id) {
       return repository.getOrderById(id);
    }

    public List<OrderTechnicalForServiceEntity> getAllOrderForService(){
        return repository.getAllOrderForServiceList();
    }

    @Transactional
    public boolean updateOrderStatus(OrderStatus orderStatus, Integer orderId) {

        OrderTechnicalForServiceEntity orderDb = repository.getOrderById(orderId);
        orderDb.setOrderStatus(orderStatus);
        orderDb.forUpdate(SecurityUtils.getUserId());

        repository.save(orderDb);

        return true;
    }
}
