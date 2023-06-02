package uz.BTService.btservice.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@AllArgsConstructor
public class AttachDownloadDTO {
    private Resource resource;
    private String contentType;

}
