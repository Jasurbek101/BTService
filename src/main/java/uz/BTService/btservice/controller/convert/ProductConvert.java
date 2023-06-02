package uz.BTService.btservice.controller.convert;

import lombok.experimental.UtilityClass;
import uz.BTService.btservice.controller.dto.CategoryDto;
import uz.BTService.btservice.controller.dto.response.ProductResponseForAdminDto;
import uz.BTService.btservice.controller.dto.request.ProductCreateRequestDto;
import uz.BTService.btservice.controller.dto.response.AttachResponseDto;
import uz.BTService.btservice.controller.dto.response.ProductResponseForUserDto;
import uz.BTService.btservice.entity.AttachEntity;
import uz.BTService.btservice.entity.ProductEntity;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ProductConvert {

    public ProductEntity convertToEntity(ProductCreateRequestDto productCreateRequest){
        ProductEntity product = productCreateRequest.toEntity("attach", "attachId", "categoryId", "category");
        product.setCategoryId(productCreateRequest.getCategoryId());
        product.setAttach(setProductSetAttachId(productCreateRequest.getAttachId()));
        return product;
    }

    public ProductResponseForAdminDto fromForAdmin(ProductEntity product){
        ProductResponseForAdminDto productResponseForAdminDto = product.toDto("attach");
        List<AttachResponseDto> AttachDto = AttachConvert.from(product.getAttach());
        CategoryDto categoryDto = CategoryConvert.from(product.getCategory());
        productResponseForAdminDto.setAttach(AttachDto);
        productResponseForAdminDto.setCategory(categoryDto);
        return productResponseForAdminDto;
    }

    public ProductResponseForUserDto from(ProductEntity product){

        return ProductResponseForUserDto
                .builder()
                .name(product.getName())
                .price(product.getPrice())
                .categoryId(product.getCategoryId())
                .description(product.getDescription())
                .color(product.getColor())
                .attach(AttachConvert.from(product.getAttach()))
                .build();
    }

    public List<ProductResponseForAdminDto> fromForAdmin(List<ProductEntity> productEntityList){
        return productEntityList.stream().map(ProductConvert::fromForAdmin).toList();
    }

    public List<ProductResponseForUserDto> from(List<ProductEntity> productEntityList){
        return productEntityList.stream().map(ProductConvert::from).toList();
    }

    private List<AttachEntity> setProductSetAttachId(List<String> attachIdList) {
        List<AttachEntity> attachEntities = new ArrayList<>();

        for (String id : attachIdList) {
            AttachEntity attach = new AttachEntity();
            attach.setId(id);
            attachEntities.add(attach);
        }
        return attachEntities;
    }

}
