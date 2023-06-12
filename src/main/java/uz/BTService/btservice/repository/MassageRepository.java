package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.BTService.btservice.entity.MessageEntity;

import java.util.List;

@Repository
public interface MassageRepository extends JpaRepository<MessageEntity, Integer> {
    List<MessageEntity> findByOrderServiceIdNotNull();
    List<MessageEntity> findByOrderForProductIdNotNull();

    void deleteAllByIdIn(List<Integer> id);
}
