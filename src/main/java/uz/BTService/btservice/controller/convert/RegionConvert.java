package uz.BTService.btservice.controller.convert;

import lombok.experimental.UtilityClass;
import uz.BTService.btservice.controller.dto.CategoryDto;
import uz.BTService.btservice.controller.dto.RegionDto;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.RegionEntity;

import java.util.List;


@UtilityClass
public class RegionConvert {
    public RegionEntity convertToEntity(RegionDto regionDto){
        return regionDto.toEntity();
    }

    public RegionDto from(RegionEntity region){
        return region.toDto();
}

    public RegionDto fromTree(RegionEntity region){
        return region.getDto(true);
    }

    public RegionDto fromNoTree(RegionEntity region){
        return region.getDto(false);
    }

    public List<RegionDto> fromNoTree(List<RegionEntity> regionEntityList){
        return regionEntityList.stream().map(RegionConvert::fromNoTree).toList();
    }
}
