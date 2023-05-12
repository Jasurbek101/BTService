package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.dto.CategoryDto;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryEntity addCategory(CategoryDto categoryDto) {
        Long userId = SecurityUtils.getUserId();

        Optional<CategoryEntity> byCreatedByName = categoryRepository.findByCreatedByName(categoryDto.getName());
        if (byCreatedByName.isPresent()) {
            CategoryEntity categoryEntity = byCreatedByName.get();
            if (categoryEntity.getStatus().equals(EntityStatus.DELETED)) {
                categoryEntity.forCreate(userId);
                categoryEntity.setStatus(EntityStatus.CREATED);
                return categoryRepository.save(categoryEntity);
            }
        }
        CategoryEntity entity = categoryDto.toEntity();
        entity.forCreate(userId);
        return categoryRepository.save(entity);
    }

    public CategoryEntity getCategoryId(Long id) {
        return categoryRepository.findByCategoryId(id).orElseThrow(() -> new RuntimeException(id + " not found!!!"));
    }

    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAllCategory();
    }

    public List<CategoryEntity> getAllIdCategory(Long id) {
        return categoryRepository.findAllById(id);
    }

    @Transactional
    public Boolean delete(Long id) {
        return categoryRepository.categoryDelete(id) > 0;
    }

    public CategoryEntity updateCategory(CategoryDto categoryDto) {
        if (!categoryDto.getName().isEmpty()) {

            CategoryEntity entity = categoryRepository.findByCategoryId(categoryDto.getId()).orElseThrow(
                    () -> new RuntimeException(categoryDto.getId() + " id not found!!!"));

            entity.setName(categoryDto.getName());
            entity.forUpdate(SecurityUtils.getUserId());

            return categoryRepository.save(entity);
        }
        return null;
    }


}
