package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.BTService.btservice.controller.convert.BannerConvert;
import uz.BTService.btservice.controller.convert.ProductConvert;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.request.BannerCreateRequestDto;
import uz.BTService.btservice.controller.dto.request.ProductCreateRequestDto;
import uz.BTService.btservice.entity.BannerEntity;
import uz.BTService.btservice.entity.ProductEntity;
import uz.BTService.btservice.service.BannerService;


@RestController
@RequestMapping("/api/v1/banner")
@RequiredArgsConstructor
@Tag(name = "Banner", description = "This Banner CRUD")
public class BannerController {

    private final BannerService bannerService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for post", description = "This method Product add")
    @PostMapping("/add")
    public HttpResponse<Object> addProduct(@RequestBody BannerCreateRequestDto bannerCreateRequestDto) {

        HttpResponse<Object> response = HttpResponse.build(false);

        BannerEntity bannerEntity = BannerConvert.convertToEntity(bannerCreateRequestDto);
        boolean save = bannerService.saveBanner(bannerEntity);

//        ProductEntity product = ProductConvert.convertToEntity(productDto);
//        boolean isSave = productService.addProduct(product, responses);

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(null)
                .message(HttpResponse.Status.OK.name());

    }
}
