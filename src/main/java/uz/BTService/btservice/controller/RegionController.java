package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.BTService.btservice.controller.convert.CategoryConvert;
import uz.BTService.btservice.controller.convert.RegionConvert;
import uz.BTService.btservice.controller.dto.CategoryDto;
import uz.BTService.btservice.controller.dto.RegionDto;
import uz.BTService.btservice.controller.dto.dtoUtil.HttpResponse;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.RegionEntity;
import uz.BTService.btservice.service.RegionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
@RequiredArgsConstructor
@Tag(name = "Region", description = "This Region CRUD")
public class RegionController {

    private final RegionService regionService;
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for post", description = "This method Region add")
    @PostMapping("/add")
    public HttpResponse<Object> addRegion(@RequestBody RegionDto regionDto) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
             RegionEntity region= regionService.addRegion(RegionConvert.convertToEntity(regionDto));
            response
                    .code(HttpResponse.Status.OK)
                    .success(true)
                    .body(RegionConvert.from(region))
                    .message(HttpResponse.Status.OK.name());
        } catch (Exception e) {
            response
                    .code(HttpResponse.Status.INTERNAL_SERVER_ERROR)
                    .message(e.getMessage());
        }
        return response;
    }

    @PreAuthorize("permitAll()")
    @Operation(summary = "This method for getId", description = "This method Region getId")
    @GetMapping("/get/tree/{id}")
    public HttpResponse<Object> getRegionIdTree(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);

        RegionEntity region = regionService.getRegionIdTree(id);
        if (region != null) {response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(RegionConvert.fromTree(region))
                .message(HttpResponse.Status.OK.name());
        } else {
            response
                    .code(HttpResponse.Status.NOT_FOUND)
                    .success(true)
                    .message(id + " is region not found!!!");
        }

        return response;
    }

    @PreAuthorize("permitAll()")
    @Operation(summary = "This method for getId", description = "This method Region getId")
    @GetMapping("/get/{id}")
    public HttpResponse<Object> getRegionId(@PathVariable Integer id) {

        HttpResponse<Object> response = HttpResponse.build(false);

        RegionEntity region = regionService.getRegionId(id);

        return response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(RegionConvert.fromNoTree(region))
                .message(HttpResponse.Status.OK.name());
    }

    @PreAuthorize("permitAll()")
    @Operation(summary = "This method for getAll", description = "This method user getAll")
    @GetMapping("/get/all")
    public HttpResponse<Object> getAllRegion() {
        HttpResponse<Object> response = HttpResponse.build(false);
        List<RegionEntity> allRegion = regionService.getAllRegion();
        response
                .code(HttpResponse.Status.OK)
                .success(true)
                .body(RegionConvert.fromNoTree(allRegion))
                .message(HttpResponse.Status.OK.name());


        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for Put", description = "This method user update")
    @PostMapping("/update")
    public HttpResponse<Object> update(@RequestBody RegionDto regionDto) {
        HttpResponse<Object> response = HttpResponse.build(false);

        RegionEntity region = regionService.updateRegion(regionDto.toEntity());
        RegionDto regionDto1 = RegionConvert.fromTree(region);

        response.code(HttpResponse.Status.OK)
                .success(true)
                .body(regionDto1)
                .message(HttpResponse.Status.OK.name());

        return response;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @Operation(summary = "This method for Delete", description = "This method user delete")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> deleteRegion(@PathVariable Integer id) {
        HttpResponse<Object> response = HttpResponse.build(false);

        Boolean isDelete = regionService.delete(id);

        return response.code(HttpResponse.Status.OK)
                .success(true)
                .body(isDelete)
                .message(HttpResponse.Status.OK.name());

    }
}
