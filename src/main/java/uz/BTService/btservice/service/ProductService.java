package uz.BTService.btservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.DateUtil;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.controller.dto.response.AttachResponseDto;
import uz.BTService.btservice.controller.dto.response.ProductResponseForAdminDto;
import uz.BTService.btservice.controller.dto.dtoUtil.DataGrid;
import uz.BTService.btservice.controller.dto.dtoUtil.FilterForm;
import uz.BTService.btservice.entity.AttachEntity;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.ProductEntity;
import uz.BTService.btservice.exceptions.CategoryNotFoundException;
import uz.BTService.btservice.exceptions.ProductNotFoundException;
import uz.BTService.btservice.repository.CategoryRepository;
import uz.BTService.btservice.repository.ProductRepository;

import java.text.ParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository repository;

    private final CategoryRepository categoryRepository;

    public DataGrid<ProductEntity> productPage(HttpServletRequest request, FilterForm filterForm) throws Exception {
        DataGrid<ProductEntity> dataGrid = new DataGrid<>();
        dataGrid.setRows(getProductAllList());
        return dataGrid;
    }

    public boolean addProduct(ProductEntity newProduct, Integer categoryId) {

        CategoryEntity categoryIdDb = categoryRepository.findByCategoryId(categoryId).orElseThrow(
                () -> {
                    throw new CategoryNotFoundException(categoryId + "-id category not found!!!");
                }
        );

        newProduct.setCategory(categoryIdDb);
        newProduct.forCreate(SecurityUtils.getUserId());

        repository.save(newProduct);

        return true;
    }

    public ProductEntity getById(Integer id) {

        return repository.findByProductId(id).orElseThrow(() -> {
                    throw new ProductNotFoundException(id + " product id not found");
                }
        );
    }

    public List<ProductEntity> getProductAllList() {
        return repository.getAllProduct();
    }

    @Transactional
    public Boolean delete(Integer id) {
        Integer integer = repository.productDeleted(id, SecurityUtils.getUserId());
        return integer > 0;
    }

    public List<ProductEntity> getByCategoryId(Integer id) {
        if (id != null) {
            return repository.getCategoryId(id);
        } else {
            throw new CategoryNotFoundException("id is null");
        }
    }

    public List<ProductEntity> getProductNameSearch(String productName) {
        return repository.getProductNameListSearch(searchProductNameToArray(productName));
    }

    private String[] searchProductNameToArray(String productName) {
        String[] categoryNameList = productName.split(" ");

        for (byte i = 0; i < categoryNameList.length; i++) {
            categoryNameList[i] = "%" + categoryNameList[i] + "%";
        }
        return categoryNameList;
    }

    public List<ProductEntity> getDeletedProductsByDate(FilterForm filterForm) {

        Map<String, Object> filterMap = filterForm.getFilter();

        Date startDate = null;
        Date endDate = null;
        if (filterMap != null) {
            if (filterMap.containsKey("startDate")) {
                try {
                    startDate = DateUtils.parseDate((MapUtils.getString(filterMap, "startDate")), DateUtil.PATTERN3);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (filterMap.containsKey("endDate")) {
                try {
                    endDate = DateUtils.parseDate((MapUtils.getString(filterMap, "endDate")), DateUtil.PATTERN3);
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

        return repository.getDeletedProductByDate(startDate, endDate);

    }
}
