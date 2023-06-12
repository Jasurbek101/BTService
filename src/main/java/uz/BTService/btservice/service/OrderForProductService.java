package uz.BTService.btservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.constants.MassageText;
import uz.BTService.btservice.constants.OrderStatus;
import uz.BTService.btservice.entity.MessageEntity;
import uz.BTService.btservice.entity.OrderForProductEntity;
import uz.BTService.btservice.repository.MassageRepository;
import uz.BTService.btservice.repository.OrderForProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderForProductService {

    private final OrderForProductRepository repository;

    private final MassageRepository massageRepository;

    public boolean addOrder(OrderForProductEntity orderForProduct) {
        MessageEntity message = new MessageEntity();

        message.setOrderForProductId(orderForProduct.getProductId());
        message.setText(MassageText.ORDER_SERVICE_CREATE);

        orderForProduct.setOrderStatus(OrderStatus.NEW);
        repository.save(orderForProduct);
        massageRepository.save(message);

        return true;
    }

    public List<OrderForProductEntity> getAllOrderForService() {
        return repository.getAllOrderProduct();
    }

    public OrderForProductEntity getOrderById(Integer id) {
        return repository.getOrderForProductById(id);
    }

    public boolean updateOrderStatus(OrderStatus orderStatus, Integer id) {

        OrderForProductEntity orderForProductEntity = repository.getOrderForProductById(id);

        orderForProductEntity.setOrderStatus(orderStatus);
        orderForProductEntity.forUpdate(SecurityUtils.getUserId());

        return true;
    }
}
