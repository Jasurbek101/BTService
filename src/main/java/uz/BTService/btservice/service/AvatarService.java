package uz.BTService.btservice.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvatarService {
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    public Avatar uploadAvatar(MultipartHttpServletRequest request) throws IOException {
        UserEntity userEntity = userRepository.findByUsername(SecurityUtils.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User no active"));
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file!=null && file.getSize()!=0) {
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();
            Avatar attachment = new Avatar();
            attachment.setFileOriginalName(originalFilename);
            attachment.setSize(size);
            attachment.setContentType(contentType);
            attachment.forCreate(userEntity.getId());
            attachment.setMainContent(file.getBytes());
            attachment.forCreate(userEntity.getId());
            avatarRepository.save(attachment);
            return attachment;
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
