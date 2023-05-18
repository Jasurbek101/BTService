package uz.BTService.btservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.dto.AttachResponseDTO;
import uz.BTService.btservice.dto.ProductDto;
import uz.BTService.btservice.dto.response.DataGrid;
import uz.BTService.btservice.dto.response.FilterForm;
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

    public ProductDto addProduct(ProductDto productDto, HttpServletResponse responses){

        ProductEntity newProduct = productDto.toEntity("attach");

        List<String> attachIdList = productDto.getAttachId();
        List<AttachEntity> attachEntities = new ArrayList<>();

        for (String id: attachIdList){
            AttachEntity attach = new AttachEntity();
            attach.setId(id);
            attachEntities.add(attach);
        }


        newProduct.setAttach(attachEntities);
        newProduct.forCreate();

        return productRepository.save(newProduct).toDto();

    }

    public DataGrid<ProductDto> productPage(HttpServletRequest request, FilterForm filterForm) throws Exception {
        DataGrid<ProductDto> dataGrid = new DataGrid<>();
        dataGrid.setRows(getProductAllList());
        return dataGrid;
    }
    public List<ProductDto> getProductAllList(){
        List<ProductEntity> productAll = productRepository.getAllProduct();
        return productToDto(productAll);
    }

    public ProductEntity getById(Integer id){
        return productRepository.findByProductId(id).orElseThrow(()->{
                   throw  new ProductNotFoundException(id+" product id not found");
                }
        );
    }
    @Transactional
    public Boolean delete(Integer id) {
        Integer integer = productRepository.productDeleted(id);
        return integer > 0;

    }

    public List<ProductDto> getByCategoryId(Integer id) {
        if(id!=null){
           List<ProductEntity> productList = productRepository.getCategoryId(id);
           return productToDto(productList);
        }else {
           throw new CategoryNotFoundException("id is null");
        }
    }
    private List<ProductDto> productToDto(List<ProductEntity> productEntityList){
        List<ProductDto> productDtoList = new ArrayList<>();

        for(ProductEntity product:productEntityList){

            List<AttachResponseDTO> attachResponseDTOList = new ArrayList<>();

            for(AttachEntity attach: product.getAttach()){

                AttachResponseDTO attachResponseDTO = new AttachResponseDTO();
                BeanUtils.copyProperties(attach,attachResponseDTO);
                attachResponseDTO.setUrl(attachDownloadUrl + attach.getId() + "." + attach.getType());
                attachResponseDTOList.add(attachResponseDTO);

            }

            ProductDto pro= product.toDto("attach");
            pro.setAttach(attachResponseDTOList);
            productDtoList.add(pro);
        }
        return productDtoList;
    }

}
