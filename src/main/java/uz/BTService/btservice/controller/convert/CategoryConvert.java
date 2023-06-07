package uz.BTService.btservice.controller.convert;

import lombok.experimental.UtilityClass;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.controller.dto.CategoryDto;
import uz.BTService.btservice.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CategoryConvert {

    public CategoryEntity convertToEntity(CategoryDto categoryDto){
        return categoryDto.toEntity();
    }

    public CategoryDto fromTree(CategoryEntity category){

        CategoryDto dto = category.toDto("children");
        dto.setChildren(fromTree(category.getChildren()));

        return dto;
    }

    public CategoryDto fromNoTree(CategoryEntity category){
        return category.toDto("children");
    }


    public List<CategoryDto> fromTree(List<CategoryEntity> categoryList){
        return categoryList.stream().map(CategoryConvert::fromTree).toList();
    }

    public List<CategoryDto> fromNoTree(List<CategoryEntity> categoryList){
        return categoryList.stream().map(CategoryConvert::fromNoTree)
                .filter(p -> p.getStatus() != EntityStatus.DELETED).toList();
    }

}
