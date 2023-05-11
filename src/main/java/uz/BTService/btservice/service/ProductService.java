package uz.BTService.btservice.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.dto.ProductDto;
import uz.BTService.btservice.entity.Avatar;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.ProductEntity;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.repository.ProductRepository;
import uz.BTService.btservice.repository.UserRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final AvatarService avatarService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public ProductEntity addProduct(ProductDto productDto, HttpServletResponse responses){
        UserEntity userEntity = userRepository.findByUsername(SecurityUtils.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User no active"));
        try {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(productDto.getName());
            productEntity.setPrice(productDto.price);
            productEntity.setDescription(productDto.getDescription());
            Avatar avatar = avatarService.getAvatar(productDto.getAttachmentId(), responses);
            System.out.println(avatar.getFileOriginalName()+"//////////////////////////////////////////////////////////////");
            productEntity.setAvatar(avatar);
            CategoryEntity category = categoryService.getCategory(productDto.getCategoryEntityId());
            System.out.println(category+"????????????????????????????????????????????");
            productEntity.setCategoryEntity(category);
            productEntity.forCreate(userEntity.getId());
            productRepository.save(productEntity);
            return productEntity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
