package uz.BTService.btservice.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.dto.CategoryDto;
import uz.BTService.btservice.dto.response.HttpResponse;
import uz.BTService.btservice.entity.AttachmentContentEntity;
import uz.BTService.btservice.entity.AttachmentEntity;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.repository.AttachmentContentEntityRepository;
import uz.BTService.btservice.repository.AttachmentEntityRepository;
import uz.BTService.btservice.repository.UserRepository;
import uz.BTService.btservice.service.AttachmentService;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/attachment")
@RequiredArgsConstructor
@Tag(name = "Attachment", description = "This Attachment C R")
public class AttachmentEntityController {

    private final AttachmentEntityRepository attachmentEntityRepository;
    private final AttachmentContentEntityRepository attachmentContentEntityRepository;
    private final UserRepository userRepository;
    private final AttachmentService attachmentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/uploadFile")
    public HttpResponse<Object> uploadFileToDB(MultipartHttpServletRequest request) throws IOException {
        HttpResponse<Object> response = HttpResponse.build(false);
        try{
             response.code(HttpResponse.Status.OK).success(true).body(attachmentService.uploadAttachment(request)).message("successfully!!!");
        }catch (Exception e){
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(e.getMessage());
        }
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getFileDB/{id}")
    public HttpResponse<Object> getFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        HttpResponse<Object> responses = HttpResponse.build(false);
        try{
                AttachmentEntity attachment= attachmentService.getAttachment(id,response);
                responses.code(HttpResponse.Status.OK).success(true).body(attachment)
                        .message("successfully!!!");
        }catch (Exception e){
            responses.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).message(e.getMessage());
        }
        return responses;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public HttpResponse<Object> updateFileToDB(MultipartHttpServletRequest request, @PathVariable Long id) throws IOException {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(attachmentService.updateAttachment(id,request))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(" error");
        }
        return response;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public HttpResponse<Object> deleteCategory(@PathVariable Long id) {
        HttpResponse<Object> response = HttpResponse.build(false);
        try {
            response.code(HttpResponse.Status.OK).success(true).body(attachmentService.delete(id))
                    .message("successfully!!!");
        } catch (Exception e) {
            response.code(HttpResponse.Status.INTERNAL_SERVER_ERROR).success(false).message(id + " not found!!!");
        }
        return response;
    }

}
