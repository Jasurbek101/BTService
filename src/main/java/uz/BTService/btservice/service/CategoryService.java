package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.exceptions.CategoryNotFoundException;
import uz.BTService.btservice.repository.CategoryRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryEntity addCategory(CategoryEntity category) {
        Integer userId = SecurityUtils.getUserId();

        Optional<CategoryEntity> byCreatedByName = categoryRepository.findByCreatedByName(category.getName());

        if (byCreatedByName.isPresent()) {
            return categoryStatusCheckAndSave(byCreatedByName, category, userId);
        }

        category.forCreate(userId);

        return categoryRepository.save(category);
    }

    public CategoryEntity getCategoryIdTree(Integer categoryId) {
        if (categoryId == null) return null;
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> {
                    throw new CategoryNotFoundException(categoryId + "-id not found");
                }
        );
    }

    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findAllCategory();
    }

    @Transactional
    public Boolean delete(Integer id) {
        if (id != null) {
            categoryRepository.findByCategoryId(id).orElseThrow(
                    () -> new CategoryNotFoundException(id + " id not found!!!"));
        }
        categoryRepository.categoryDelete(id);
        return true;
    }

    public List<CategoryEntity> getAllTreeCategory() {
        return categoryRepository.getCategoryTree();
    }

    public CategoryEntity updateCategory(CategoryEntity category) {

        CategoryEntity entity = childIdAndParentIdVerify(category);

        entity.setParentId(category.getParentId());
        entity.setName(category.getName());
        entity.forUpdate(SecurityUtils.getUserId());

        return categoryRepository.save(entity);
    }


    public CategoryEntity getCategoryId(Integer categoryId) {
        if (categoryId == null) return null;

        return categoryRepository.findById(categoryId).orElseThrow(
                () -> {throw new CategoryNotFoundException(categoryId + "-id not found!!!");}
        );
    }

    private CategoryEntity categoryStatusCheckAndSave(Optional<CategoryEntity> byCreatedByName, CategoryEntity categoryentity, Integer userId) {

        CategoryEntity categoryEntity = byCreatedByName.get();
        if (categoryEntity.getStatus() == EntityStatus.DELETED) {

            categoryEntity.setName(categoryentity.getName());

            if (categoryentity.getParentId() != null) {
                categoryRepository.findByCategoryId(categoryEntity.getParentId()).orElseThrow(() -> {
                    throw new CategoryNotFoundException(categoryentity.getParentId() + " parent id not found!");
                });
                categoryEntity.setParentId(categoryentity.getParentId());
            }

            categoryEntity.setStatus(EntityStatus.CREATED);
            categoryEntity.forCreate(userId);
            return categoryRepository.save(categoryEntity);
        } else {
            throw new CategoryNotFoundException(categoryentity.getName() + " such a category exists!");
        }
    }

    private CategoryEntity childIdAndParentIdVerify(CategoryEntity category) {

        CategoryEntity entity = null;
        if (category.getParentId() != null) {

            entity = parentIdVerify(category);

        } else {
            entity = categoryRepository.findByCategoryId(category.getId()).orElseThrow(
                    () -> new CategoryNotFoundException(category.getId() + " id not found!!!"));
        }
        return entity;
    }

    private CategoryEntity parentIdVerify(CategoryEntity category) {

        CategoryEntity entity = null;
        List<CategoryEntity> parentAndChild = categoryRepository.getCategoryIdParentAndChild(category.getId(), category.getParentId());

        if(parentAndChild.size()==2){
            for (CategoryEntity categoryDB : parentAndChild) {

                if (Objects.equals(categoryDB.getId(), category.getId())) {
                    entity = categoryDB;

                }
            }

        } else {
            throw new CategoryNotFoundException("id not found!!!");
        }
        return entity;
    }


}
