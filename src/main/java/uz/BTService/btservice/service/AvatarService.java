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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        Long userId = SecurityUtils.getUserId();
        try {
            Iterator<String> fileNames = request.getFileNames();
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null && file.getSize()!=0){
                String originalFilename = file.getOriginalFilename();
                Avatar attachment = new Avatar();
                attachment.setFileOriginalName(originalFilename);
                attachment.setSize(file.getSize());
                attachment.setContentType(file.getContentType());
                String[] split = originalFilename.split("\\.");
                String name = UUID.randomUUID() + "." + split[split.length - 1];
                attachment.setName(name);
                attachment.forCreate(userId);
                avatarRepository.save(attachment);
                Path path = Paths.get(uploadDirectory+"/"+name);
                Files.copy(file.getInputStream(),path);
                return attachment;
            }
        }catch (IOException e){
            throw new FileUploadException("File could not upload");
        }
        return null;
    }


    public void getAvatar(Long id, HttpServletResponse response) throws IOException {
        Optional<Avatar> optionalAttachment = avatarRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Avatar attachment = optionalAttachment.get();
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + attachment.getFileOriginalName() + "\"");
            response.setContentType(attachment.getContentType());
            FileInputStream fileInputStream = new FileInputStream(uploadDirectory + "/" + attachment.getName());
            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
        }
            response.setStatus(404);
    }

    @Transactional
    public Boolean delete(Long id) {
        Integer integer = avatarRepository.avatarDelete(id);
        return integer > 0;
    }

    public Avatar updateAvatar(Long id, MultipartHttpServletRequest request) throws IOException {
        Long userId = SecurityUtils.getUserId();
        try {
            Iterator<String> fileNames = request.getFileNames();
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null && file.getSize()!=0){
                String originalFilename = file.getOriginalFilename();
                Optional<Avatar> byId = avatarRepository.findById(id);
                if (byId.isEmpty())
                    throw new FileNotFoundException("file not found");
                Avatar avatar = byId.get();
                avatar.setFileOriginalName(originalFilename);
                avatar.setSize(file.getSize());
                avatar.setContentType(file.getContentType());
                String[] split = originalFilename.split("\\.");
                String name = UUID.randomUUID() + "." + split[split.length - 1];
                avatar.setName(name);
                avatar.forUpdate(userId);
                avatarRepository.save(avatar);
                Path path = Paths.get(uploadDirectory+"/"+name);
                Files.copy(file.getInputStream(),path);
                return avatar;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
