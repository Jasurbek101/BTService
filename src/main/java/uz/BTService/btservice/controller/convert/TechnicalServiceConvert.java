package uz.BTService.btservice.controller.convert;


import lombok.experimental.UtilityClass;
import uz.BTService.btservice.controller.dto.request.TechnicalServiceCreate;
import uz.BTService.btservice.controller.dto.response.TechnicalServiceResponseDto;
import uz.BTService.btservice.entity.TechnicalServiceEntity;

import java.util.List;

@UtilityClass
public class TechnicalServiceConvert {

    public TechnicalServiceEntity convertToEntity(TechnicalServiceCreate technicalServiceCreate){

        return TechnicalServiceEntity
                .builder()
                .attachId(technicalServiceCreate.getAttachId())
                .description(technicalServiceCreate.getDescription())
                .build();
    }

    public TechnicalServiceResponseDto from(TechnicalServiceEntity technicalServiceEntity){

        TechnicalServiceResponseDto technicalServiceResponseDto = technicalServiceEntity.toDto("category", "attach");
        technicalServiceResponseDto.setAttachResponse(AttachConvert.from(technicalServiceEntity.getAttach()));
        technicalServiceResponseDto.setCategory(CategoryConvert.fromNoTree(technicalServiceEntity.getCategory()));

        return technicalServiceResponseDto;

    }

    public List<TechnicalServiceResponseDto> from(List<TechnicalServiceEntity> technicalServiceEntityList){
        return technicalServiceEntityList.stream().map(TechnicalServiceConvert::from).toList();
    }
}
