package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.entity.TechnicalServiceEntity;
import uz.BTService.btservice.exceptions.CategoryNotFoundException;
import uz.BTService.btservice.repository.CategoryRepository;
import uz.BTService.btservice.repository.TechnicalServiceRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TechnicalService {

    private final TechnicalServiceRepository repository;

    private final CategoryRepository categoryRepository;

    public boolean add(TechnicalServiceEntity entity, Integer categoryId){
        categoryRepository.findByCategoryId(categoryId).orElseThrow(()->{
            throw new CategoryNotFoundException(categoryId+"-id category not found");
        });
        entity.forCreate(SecurityUtils.getUserId());
        repository.save(entity);
        return true;
    }

    public TechnicalServiceEntity getById(Integer id) {
        return repository.getByTechnicalId(id);
    }

    public List<TechnicalServiceEntity> getTechnicalServiceCategoryType(Integer categoryId){
        return repository.getTechnicalServiceCategoryType(categoryId);
    }

    @Transactional
    public void deletedById(Integer id) {
        repository.technicalServiceDelete(id);
    }
}
