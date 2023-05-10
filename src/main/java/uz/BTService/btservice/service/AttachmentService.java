package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.entity.AttachmentContentEntity;
import uz.BTService.btservice.repository.AttachmentContentEntityRepository;
import uz.BTService.btservice.repository.AttachmentEntityRepository;

@Service
@RequiredArgsConstructor
public class AttachmentService {

private final AttachmentContentEntityRepository attachmentContent;
private final AttachmentEntityRepository attachmentEntityRepository;
    @Transactional
    public Boolean delete(Long id) {
        Integer integer = attachmentEntityRepository.attachmentDelete(id);
        Integer integer1 = attachmentContent.attachmentDelete(id);
        return integer > 0 && integer1 > 0;
    }
}
