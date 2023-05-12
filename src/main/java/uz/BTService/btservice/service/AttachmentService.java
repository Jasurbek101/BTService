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
import uz.BTService.btservice.entity.AttachmentContentEntity;
import uz.BTService.btservice.entity.AttachmentEntity;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.repository.AttachmentContentEntityRepository;
import uz.BTService.btservice.repository.AttachmentEntityRepository;
import uz.BTService.btservice.repository.UserRepository;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentContentEntityRepository attachmentContent;
    private final AttachmentEntityRepository attachmentEntityRepository;
    private final UserRepository userRepository;
    private final AttachmentContentEntityRepository attachmentContentEntityRepository;

    public AttachmentContentEntity uploadAttachment(MultipartHttpServletRequest request) throws IOException {
        UserEntity userEntity = userRepository.findByUsername(SecurityUtils.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User no active"));
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file!=null && file.getSize()!=0) {
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();
            AttachmentEntity attachment = new AttachmentEntity();
            attachment.setFileOriginalName(originalFilename);
            attachment.setSize(size);
            attachment.setContentType(contentType);
            attachment.forCreate(userEntity.getId());
            AttachmentEntity saveAttachment = attachmentEntityRepository.save(attachment);
            AttachmentContentEntity attachmentContent = new AttachmentContentEntity();
            attachmentContent.setMainContent(file.getBytes());
            attachmentContent.setAttachmentEntity(saveAttachment);
            attachmentContent.forCreate(userEntity.getId());
            attachmentContentEntityRepository.save(attachmentContent);
            return attachmentContent;
        }
        return null;
    }


    public AttachmentEntity getAttachment(Long id, HttpServletResponse response) throws IOException {
        Optional<AttachmentEntity> optionalAttachment = attachmentEntityRepository.findById(id);
        if (optionalAttachment.isPresent()){
            AttachmentEntity attachment = optionalAttachment.get();
            Optional<AttachmentContentEntity> contentOptional = attachmentContentEntityRepository.findByAttachmentEntityId(id);
            if (contentOptional.isPresent()){
                AttachmentContentEntity attachmentContent = contentOptional.get();
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + attachment.getFileOriginalName() + "\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());
            }
            return null;
    }
        return optionalAttachment.get();
    }

    @Transactional
    public Boolean delete(Long id) {
        Integer integer = attachmentEntityRepository.attachmentDelete(id);
        Integer integer1 = attachmentContent.attachmentDelete(id);
        return integer > 0 && integer1 > 0;
    }


    public AttachmentContentEntity updateAttachment(Long id, MultipartHttpServletRequest request) throws IOException {
        UserEntity userEntity = userRepository.findByUsername(SecurityUtils.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User no active"));
        Optional<AttachmentEntity> attachmentEntity = attachmentEntityRepository.findById(id);
        Optional<AttachmentContentEntity> byAttachmentEntityId = attachmentContentEntityRepository.findByAttachmentEntityId(id);
        if ((!attachmentEntity.isEmpty()) && (!byAttachmentEntityId.isEmpty())){
            AttachmentEntity attachmentEntity1 = attachmentEntity.get();
            Iterator<String> fileNames = request.getFileNames();
            MultipartFile file = request.getFile(fileNames.next());
            if (file!=null && file.getSize()!=0){
                long size = file.getSize();
                String contentType = file.getContentType();
                attachmentEntity1.setFileOriginalName(file.getOriginalFilename());
                attachmentEntity1.setSize(size);
                attachmentEntity1.setContentType(contentType);
                attachmentEntity1.forUpdate(userEntity.getId());
                attachmentEntityRepository.save(attachmentEntity1);
                AttachmentContentEntity attachmentContentEntity = byAttachmentEntityId.get();
                attachmentContentEntity.setMainContent(file.getBytes());
                attachmentContentEntity.setAttachmentEntity(attachmentEntity1);
                attachmentContentEntity.forUpdate(userEntity.getId());
                attachmentContentEntityRepository.save(attachmentContentEntity);
                return attachmentContentEntity;
            }
            return null;
        }
        return null;
    }





}