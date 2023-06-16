package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.controller.convert.BannerConvert;
import uz.BTService.btservice.controller.convert.ProductConvert;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.controller.dto.request.BannerCreateRequestDto;
import uz.BTService.btservice.controller.dto.request.ProductCreateRequestDto;
import uz.BTService.btservice.controller.dto.response.BannerResponseDto;
import uz.BTService.btservice.controller.dto.response.ProductResponseForUserDto;
import uz.BTService.btservice.entity.BannerEntity;
import uz.BTService.btservice.entity.ProductEntity;
import uz.BTService.btservice.service.BannerService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/banner")
@RequiredArgsConstructor
@Tag(name = "Banner", description = "This Banner CRUD")
public class BannerController {

    private final BannerService bannerService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('CONTEND_MANAGER','SUPER_ADMIN')")
    @Operation(summary = "This method for post", description = "This method Product add")
    @PostMapping("/add")
    public HttpResponse<Object> addProduct(@RequestBody BannerCreateRequestDto bannerCreateRequestDto) {

        HttpResponse<Object> response = HttpResponse.build(false);

        BannerEntity bannerEntity = BannerConvert.convertToEntity(bannerCreateRequestDto);
        boolean save = bannerService.saveBanner(bannerEntity);

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(save)
                .message(HttpResponse.Status.OK.name());
    }


    @Operation(summary = "This method for GetId", description = "This method Banner GetId")
    @GetMapping("/get/all")
    public HttpResponse<Object> getBannerAll() {
        HttpResponse<Object> response = HttpResponse.build(false);

        List<BannerEntity> banner = bannerService.getByAll();
        List<BannerResponseDto> bannerResponseDtoList = BannerConvert.from(banner);
        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(bannerResponseDtoList)
                .message(HttpResponse.Status.OK.name());

    }

    @Operation(summary = "This method for GetId", description = "This method Banner GetId")
    @GetMapping("/get/{id}")
    public HttpResponse<Object> getBannerId(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);

        BannerEntity banner = bannerService.getById(id);
        BannerResponseDto bannerResponseDto = BannerConvert.from(banner);

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(bannerResponseDto)
                .message(HttpResponse.Status.OK.name());

    }

}
