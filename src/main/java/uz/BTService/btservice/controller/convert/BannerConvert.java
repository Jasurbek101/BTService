package uz.BTService.btservice.controller.convert;

import lombok.experimental.UtilityClass;
import uz.BTService.btservice.controller.dto.request.BannerCreateRequestDto;
import uz.BTService.btservice.controller.dto.response.BannerResponseDto;
import uz.BTService.btservice.entity.BannerEntity;

import java.util.List;

@UtilityClass
public class BannerConvert {

    public BannerEntity convertToEntity(BannerCreateRequestDto bannerCreateRequestDto){
        return BannerEntity
                .builder()
                .name(bannerCreateRequestDto.getName())
                .attachId(bannerCreateRequestDto.getAttachId())
                .position(bannerCreateRequestDto.getPosition())
                .build();
    }

    public BannerResponseDto from(BannerEntity banner){
        return BannerResponseDto
                .builder()
                .id(banner.getId())
                .position(banner.getPosition())
                .attach(AttachConvert.from(banner.getAttach()))
                .name(banner.getName())
                .build();
    }

    public List<BannerResponseDto> from(List<BannerEntity> bannerEntityList){
        return bannerEntityList.stream().map(BannerConvert::from).toList();
    }

}
