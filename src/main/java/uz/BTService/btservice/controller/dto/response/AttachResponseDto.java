package uz.BTService.btservice.controller.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AttachResponseDto {
    private String id;
    private String originalName;
    private String path;
    private Long size;
    private String type;
    private Double duration;
    private LocalDateTime createdData;
    private String url;
}
