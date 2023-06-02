package uz.BTService.btservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.controller.dto.response.AttachResponseDto;
import uz.BTService.btservice.controller.dto.response.ProductResponseForAdminDto;
import uz.BTService.btservice.controller.dto.dtoUtil.DataGrid;
import uz.BTService.btservice.controller.dto.dtoUtil.FilterForm;
import uz.BTService.btservice.entity.AttachEntity;
import uz.BTService.btservice.entity.ProductEntity;
import uz.BTService.btservice.exceptions.CategoryNotFoundException;
import uz.BTService.btservice.exceptions.ProductNotFoundException;
import uz.BTService.btservice.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Value("${attach.download.url}")
    private String attachDownloadUrl;

    private final ProductRepository productRepository;

    public DataGrid<ProductEntity> productPage(HttpServletRequest request, FilterForm filterForm) throws Exception {
        DataGrid<ProductEntity> dataGrid = new DataGrid<>();
        dataGrid.setRows(getProductAllList());
        return dataGrid;
    }

    public boolean addProduct(ProductEntity newProduct, HttpServletResponse responses) {

        newProduct.forCreate(SecurityUtils.getUserId());

        productRepository.save(newProduct);

        return true;
    }

    public ProductEntity getById(Integer id) {

        return productRepository.findByProductId(id).orElseThrow(() -> {
                    throw new ProductNotFoundException(id + " product id not found");
                }
        );
    }

    public List<ProductEntity> getProductAllList() {
        return productRepository.getAllProduct();
    }

    @Transactional
    public Boolean delete(Integer id) {
        Integer integer = productRepository.productDeleted(id);
        return integer > 0;
    }

    public List<ProductEntity> getByCategoryId(Integer id) {
        if (id != null) {
            return productRepository.getCategoryId(id);
        } else {
            throw new CategoryNotFoundException("id is null");
        }
    }

    public List<ProductEntity> getProductNameSearch(String productName) {
       return productRepository.getProductNameListSearch(searchProductNameToArray(productName));
    }

    private List<ProductResponseForAdminDto> productToDto(List<ProductEntity> productEntityList) {

        List<ProductResponseForAdminDto> productResponseForAdminDtoList = new ArrayList<>();
        for (ProductEntity product : productEntityList) {

            List<AttachResponseDto> attachResponseDtoList = productAttachToAttachResponseDto(product.getAttach());

            ProductResponseForAdminDto pro = product.toDto("attach", "attachId", "categoryId", "category");
            pro.setCategory(product.getCategory().toDto());
            pro.setCategoryId(product.getCategoryId());
            pro.setAttach(attachResponseDtoList);
            productResponseForAdminDtoList.add(pro);
        }
        return productResponseForAdminDtoList;

    }

    private List<AttachResponseDto> productAttachToAttachResponseDto(List<AttachEntity> attachEntitieList) {
        List<AttachResponseDto> attachResponseDtoList = new ArrayList<>();

        for (AttachEntity attach : attachEntitieList) {

            AttachResponseDto attachResponseDTO = new AttachResponseDto();
            BeanUtils.copyProperties(attach, attachResponseDTO);
            attachResponseDTO.setUrl(attachDownloadUrl + attach.getId() + "." + attach.getType());
            attachResponseDtoList.add(attachResponseDTO);

        }
        return attachResponseDtoList;

    }

    private String[] searchProductNameToArray(String productName) {
        String[] categoryNameList = productName.split(" ");

        for (byte i = 0; i < categoryNameList.length; i++) {
            categoryNameList[i] = "%" + categoryNameList[i] + "%";
        }
        return categoryNameList;
    }

}
