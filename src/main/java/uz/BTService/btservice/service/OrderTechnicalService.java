package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.BTService.btservice.constants.MassageText;
import uz.BTService.btservice.entity.MessageEntity;
import uz.BTService.btservice.entity.OrderTechnicalServiceEntity;
import uz.BTService.btservice.repository.OrderTechnicalServiceRepository;

@Service
@RequiredArgsConstructor
public class OrderTechnicalService {

    private final OrderTechnicalServiceRepository repository;

    public boolean addOrder(OrderTechnicalServiceEntity orderTechnicalService) {

        MessageEntity message = new MessageEntity();

        message.setOrderServiceId(orderTechnicalService.getTechnicalServiceId());
        message.setText(MassageText.ORDER_SERVICE_CREATE);

        return false;

    }
}
