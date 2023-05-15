package uz.BTService.btservice.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.dto.ProductDto;
import uz.BTService.btservice.entity.Avatar;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.ProductEntity;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.exceptionsAvatar.FileNotFoundException;
import uz.BTService.btservice.repository.AvatarRepository;
import uz.BTService.btservice.repository.CategoryRepository;
import uz.BTService.btservice.repository.ProductRepository;
import uz.BTService.btservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final AvatarRepository avatarRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    public ProductEntity addProduct(ProductDto productDto, HttpServletResponse responses){
        Long userId = SecurityUtils.getUserId();
        try {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(productDto.getName());
            productEntity.setPrice(productDto.price);
            productEntity.setDescription(productDto.getDescription());
//            Avatar avatar = avatarRepository.findById(productEntity.getId()).orElseThrow(() -> new FileNotFoundException("Avatar not found"));
            Optional<Avatar> byId = avatarRepository.findById(productDto.getAttachmentId());
            if (byId.isEmpty()){
                throw new java.io.FileNotFoundException("File not found");
            }
            Avatar avatar = byId.get();
            System.out.println(avatar.getFileOriginalName()+"//////////////////////////////////////////////////////////////");
            productEntity.setAvatar(avatar);
            CategoryEntity category = categoryService.getCategoryId(productDto.getCategoryEntityId());
            System.out.println(category+"????????????????????????????????????????????");
            productEntity.setCategoryEntity(category);
            productEntity.forCreate(userId);
            System.out.println(productEntity);
            productRepository.save(productEntity);
            System.out.println(productEntity);
            return productEntity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ProductEntity getProduct(Long id) throws FileNotFoundException {
        Optional<ProductEntity> byProductId = productRepository.findByProductId(id);
        if (byProductId.isEmpty())
            throw new FileNotFoundException("File not found");
        ProductEntity productEntity = byProductId.get();
        Optional<Avatar> byId = avatarRepository.findById(productEntity.getAvatar().getId());
        Optional<CategoryEntity> byCategoryId = categoryRepository.findByCategoryId(productEntity.getCategoryEntity().getId());
        if (byId.isEmpty()){
            throw new FileNotFoundException("Avatar not found");
        }
        if (byCategoryId.isEmpty())
            throw new FileNotFoundException("Category not found");
        productEntity.setAvatar(byId.get());
        productEntity.setCategoryEntity(byCategoryId.get());
        return productEntity;
    }

    public List<ProductEntity> getAllProduct() {
            List<ProductEntity> Prodact_List = productRepository.findByAllProduct();
        for (ProductEntity productEntity : Prodact_List) {
            Avatar avatar = avatarRepository.findByNotOptionalId(productEntity.getAvatar().getId());
            CategoryEntity category = categoryRepository.findByCategoryNotOptionalId(productEntity.getCategoryEntity().getId());
            productEntity.setCategoryEntity(category);
            productEntity.setAvatar(avatar);
        }
            return Prodact_List;
    }

    public ProductEntity updateProduct(ProductDto productDto,Long id) {
        Long userId = SecurityUtils.getUserId();
        try {
            Optional<ProductEntity> productEntity = productRepository.findByProductId(id);
            if (productEntity.isEmpty()){
                throw new java.io.FileNotFoundException("Product not found!!!!!!");
            }
            Optional<CategoryEntity> byCategoryId = categoryRepository.findByCategoryId(productDto.getCategoryEntityId());
            if (byCategoryId.isEmpty()){
                throw new java.io.FileNotFoundException("Category not found!!!!!!");
            }
            Optional<Avatar> byId = avatarRepository.findById(productDto.getAttachmentId());
            if (byId.isEmpty()){
                throw new java.io.FileNotFoundException("Avtar not found!!!!!!");
            }
            ProductEntity productEntity1 = productEntity.get();
           productEntity1.setCategoryEntity(byCategoryId.get());
           productEntity1.setName(productDto.getName());
           productEntity1.setPrice(productDto.getPrice());
           productEntity1.setAvatar(byId.get());
           productEntity1.setDescription(productDto.getDescription());
           productEntity1.forUpdate(userId);
           productRepository.save(productEntity1);
           return productEntity1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Boolean delete(Long id) {
        Integer integer = productRepository.productDeleted(id);
        return integer > 0;

    }
}
