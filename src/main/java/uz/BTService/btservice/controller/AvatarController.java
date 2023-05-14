package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.entity.Avatar;
import uz.BTService.btservice.exceptionsAvatar.FileUploadException;
import uz.BTService.btservice.service.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/avatar")
@RequiredArgsConstructor
@Tag(name = "Avatar", description = "This Avatar CRUD")
public class AvatarController {

    private final AvatarService avatarService;
    @Operation(summary = "This method for post", description = "This method Avatar add")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/uploadFile")
    public HttpResponse<Object> uploadFileToDB(MultipartHttpServletRequest request){
        HttpResponse<Object> response = HttpResponse.build(false);
        try{
            Avatar avatar = avatarService.uploadAvatar(request);
            if (avatar==null){
                throw new FileUploadException("FileUploadException");
            }
            response.code(HttpResponse.Status.OK).success(true).body(avatar).message("successfully!!!");
        }catch (Exception e){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(e.getMessage());
        }
        return response;
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This method for Get", description = "This method Avatar Get")
    @GetMapping("/getFileFromSystem/{id}")
    public void getFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        avatarService.getAvatar(id, response);
    }

    @Operation(summary = "This method for Deleted", description = "This method Avatar deleted")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> deleteAvatar(@PathVariable Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(avatarService.delete(id))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(id + " not found!!!");
        }
        return response;
    }

    @Operation(summary = "This method for Update", description = "This method Avatar Update")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public HttpResponse<Object> updateAvatar(MultipartHttpServletRequest request, @PathVariable Long id){
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(avatarService.updateAvatar(id,request))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(" error");
        }
        return response;
    }



}
