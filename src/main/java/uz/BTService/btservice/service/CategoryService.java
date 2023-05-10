package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.dto.CategoryDto;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.repository.CategoryRepository;
import uz.BTService.btservice.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryEntity addCategory(CategoryDto categoryDto) {
        UserEntity userEntity = userRepository.findByUsername(SecurityUtils.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User no active"));
        CategoryEntity entity = categoryDto.toEntity();
        entity.forCreate(userEntity.getId());
        return categoryRepository.save(entity);
    }

    public CategoryEntity getCategory(Long id) {
        return categoryRepository.findByCategoryId(id).orElseThrow(() -> new RuntimeException(id + " not found!!!"));
    }

    public List<CategoryEntity> getAllCategory() {
        List<CategoryEntity> allBy = categoryRepository.findAll();
        return  allBy;
    }

    public List<CategoryEntity> getAllIdCategory(Long id){
     List<CategoryEntity> allCategoryId = categoryRepository.findAllById(id);
     return allCategoryId;
    }

    @Transactional
    public Boolean delete(Long id) {
        return categoryRepository.categoryDelete(id) > 0;
    }

    public CategoryEntity updateCategory(CategoryDto categoryDto) {
        if(!categoryDto.getName().isEmpty()){
            UserEntity userEntity = userRepository.findByUsername(SecurityUtils.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User no active"));

            CategoryEntity entity = categoryRepository.findByCategoryId(categoryDto.getId()).orElseThrow(
                    ()-> new RuntimeException(categoryDto.getId() + " id not found!!!"));

            entity.setName(categoryDto.getName());
            entity.forUpdate(userEntity.getId());

            return categoryRepository.save(entity);
        }
        return null;
    }


}
