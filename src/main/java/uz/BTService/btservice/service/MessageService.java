package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.entity.MessageEntity;
import uz.BTService.btservice.repository.MassageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {


    private final MassageRepository repository;

    @Transactional
    public List<MessageEntity> getAllMessageForService() {

        List<MessageEntity> byOrderServiceIdNotNull = repository.findByOrderServiceIdNotNull();
        repository.deleteAllByIdIn(getListId(byOrderServiceIdNotNull));
        return byOrderServiceIdNotNull;

    }

    @Transactional
    public List<MessageEntity> getAllMessageForProduct() {

        List<MessageEntity> byOrderServiceIdNotNull = repository.findByOrderForProductIdNotNull();
        repository.deleteAllByIdIn(getListId(byOrderServiceIdNotNull));
        return byOrderServiceIdNotNull;

    }

    private List<Integer> getListId(List<MessageEntity> byOrderService){

        List<Integer> list = new ArrayList<>();
        for (MessageEntity entity : byOrderService) {
            list.add(entity.getId());
        }

        return list;
    }


}
