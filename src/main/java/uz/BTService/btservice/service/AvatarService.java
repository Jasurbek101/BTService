package uz.BTService.btservice.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.entity.Avatar;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.repository.AvatarRepository;
import uz.BTService.btservice.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private static final String uploadDirectory="downloads_file";
    public Avatar uploadAvatar(MultipartHttpServletRequest request) throws IOException {
        UserEntity userEntity = userRepository.findByUsername(SecurityUtils.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User no active"));
        try {

            Iterator<String> fileNames = request.getFileNames();
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null && file.getSize()!=0){
                String originalFilename = file.getOriginalFilename();
                Avatar attachment = new Avatar();
                attachment.setFileOriginalName(originalFilename);
                attachment.setSize(file.getSize());
                attachment.setContentType(file.getContentType());
                // Uyga.borish
                String[] split = originalFilename.split("\\.");
                String name = UUID.randomUUID() + "." + split[split.length - 1];
                attachment.setName(name);
                avatarRepository.save(attachment);
                Path path = Paths.get(uploadDirectory+"/"+name);
                Files.copy(file.getInputStream(),path);

            }
        }catch (IOException e){
            throw new FileUploadException("file not found");
        }
        return null;
    }


    public Avatar getAvatar(Long id, HttpServletResponse response) throws IOException {
        Optional<Avatar> optionalAttachment = avatarRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Avatar attachment = optionalAttachment.get();
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + attachment.getFileOriginalName() + "\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachment.getMainContent(), response.getOutputStream());
            return optionalAttachment.get();
        }
        return null;
    }

    @Transactional
    public Boolean delete(Long id) {
        Integer integer = avatarRepository.avatarDelete(id);
        return integer > 0;
    }

    public Avatar updateAvatar(Long id, MultipartHttpServletRequest request) throws IOException {
        UserEntity userEntity = userRepository.findByUsername(SecurityUtils.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User no active"));
        Optional<Avatar> avatar = avatarRepository.findById(id);
        if (!avatar.isEmpty()){
            Avatar avatar1 = avatar.get();
            Iterator<String> fileNames = request.getFileNames();
            MultipartFile file = request.getFile(fileNames.next());
            if (file!=null && file.getSize()!=0){
                long size = file.getSize();
                String contentType = file.getContentType();
                avatar1.setFileOriginalName(file.getOriginalFilename());
                avatar1.setSize(size);
                avatar1.setContentType(contentType);
                avatar1.setMainContent(file.getBytes());
                avatar1.forUpdate(userEntity.getId());
                avatarRepository.save(avatar1);
                return avatar1;
            }
            return null;
        }
        return null;
    }
}
