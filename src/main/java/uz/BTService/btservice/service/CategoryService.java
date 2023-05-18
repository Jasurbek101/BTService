package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.dto.CategoryDto;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.exceptions.CategoryNotFoundException;
import uz.BTService.btservice.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        Integer userId = SecurityUtils.getUserId();

        Optional<CategoryEntity> byCreatedByName = categoryRepository.findByCreatedByName(categoryDto.getName());
        if (byCreatedByName.isPresent()) {
            CategoryEntity categoryEntity = byCreatedByName.get();
            if (categoryEntity.getStatus().equals(EntityStatus.DELETED)) {
                categoryEntity.setName(categoryDto.getName());

                if (categoryDto.getParentId() != null) {
                    categoryRepository.findByCategoryId(categoryEntity.getParentId()).orElseThrow(() -> {
                        throw new CategoryNotFoundException(categoryDto.getParentId() + " parent id not found!");
                    });
                    categoryEntity.setParentId(categoryDto.getParentId());
                }

                categoryEntity.setStatus(EntityStatus.CREATED);
                categoryEntity.forCreate(userId);
                return categoryRepository.save(categoryEntity).getDto();
            } else {
                throw new CategoryNotFoundException(categoryDto.getName() + " such a category exists!");
            }
        }

        CategoryEntity entity = categoryDto.toEntity();
        entity.forCreate(userId);
        return categoryRepository.save(entity).getDto();
    }

    public CategoryDto getCategoryIdTree(Integer categoryId) {
        if (categoryId == null) return null;
        Optional<CategoryEntity> optRegion = categoryRepository.findById(categoryId);

        CategoryDto result = null;
        if (optRegion.isPresent()) result = optRegion.get().getDto(true);
        return result;
    }

    public List<CategoryDto> getAllCategory() {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryRepository.findAllCategory().forEach(category -> {
            categoryDtoList.add(category.toDto(category, new CategoryDto(), "children"));
        });
        return categoryDtoList;
    }

    public List<CategoryEntity> getAllIdCategory(Integer length) {
        return categoryRepository.findAllById(length);
    }

    @Transactional
    public Boolean delete(Integer id) {
        if(id!=null){
            categoryRepository.findByCategoryId(id).orElseThrow(
                    () -> new CategoryNotFoundException(id + " id not found!!!"));
        }
        categoryRepository.categoryDelete(id);
        return true;
    }

    public CategoryDto updateCategory(CategoryDto categoryDto) {
        if (!categoryDto.getName().isEmpty()) {

            CategoryEntity entity = categoryRepository.findByCategoryId(categoryDto.getId()).orElseThrow(
                    () -> new CategoryNotFoundException(categoryDto.getId() + " id not found!!!"));

            entity.setName(categoryDto.getName());
            entity.setParentId(categoryDto.getParentId());
            entity.forUpdate(SecurityUtils.getUserId());

            return categoryRepository.save(entity).getDto();
        }
        return null;
    }


    public CategoryDto getCategoryId(Integer categoryId) {
        if (categoryId == null) return null;
        Optional<CategoryEntity> optRegion = categoryRepository.findById(categoryId);

        CategoryDto result = null;
        if (optRegion.isPresent()) result = optRegion.get().getDto(false);
        return result;
    }
}
