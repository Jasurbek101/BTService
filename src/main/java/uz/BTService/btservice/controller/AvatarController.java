package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.entity.AttachmentEntity;
import uz.BTService.btservice.entity.Avatar;
import uz.BTService.btservice.service.AvatarService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/avatar")
@RequiredArgsConstructor
@Tag(name = "Avatar", description = "This Avatar CRUD")
public class AvatarController {

    private final AvatarService avatarService;
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/uploadFile")
    public HttpResponse<Object> uploadFileToDB(MultipartHttpServletRequest request) throws IOException {
        HttpResponse<Object> response = HttpResponse.build(false);
        try{
            response.code(HttpResponse.Status.OK).success(true).body(avatarService.uploadAvatar(request)).message("successfully!!!");
        }catch (Exception e){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(e.getMessage());
        }
        return response;
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getFileDB/{id}")
    public HttpResponse<Object> getFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        HttpResponse<Object> responses = HttpResponse.build(false);
        try{
            Avatar avatar= avatarService.getAvatar(id,response);
            if (avatar==null){
                throw new Exception("avatar not found");
            }
            responses.code(HttpResponse.Status.OK).success(true).body(avatar)
                    .message("successfully!!!");
        }catch (Exception e){
            responses.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
        return responses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public HttpResponse<Object> updateAvatar(MultipartHttpServletRequest request, @PathVariable Long id) throws IOException {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(avatarService.updateAvatar(id,request))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(" error");
        }
        return response;
    }

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

}
