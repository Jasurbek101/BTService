package uz.BTService.btservice.controller.convert;

import lombok.experimental.UtilityClass;
import uz.BTService.btservice.controller.dto.CategoryDto;
import uz.BTService.btservice.entity.CategoryEntity;

import java.util.List;

@UtilityClass
public class CategoryConvert {

    public CategoryEntity convertToEntity(CategoryDto categoryDto){
        return categoryDto.toEntity();
    }

    public CategoryDto fromTree(CategoryEntity category){
        if(category.getChildren()!=null){
            for (CategoryEntity cat: category.getChildren()) {
                fromTree(cat);
            }
        }
        return category.toDto();
    }

    public CategoryDto fromNoTree(CategoryEntity category){
        return category.getDto(false);
    }


    public List<CategoryDto> fromTree(List<CategoryEntity> categoryList){
        return categoryList.stream().map(CategoryConvert::fromTree).toList();
    }

    public List<CategoryDto> fromNoTree(List<CategoryEntity> categoryList){
        return categoryList.stream().map(CategoryConvert::fromNoTree).toList();
    }

}
