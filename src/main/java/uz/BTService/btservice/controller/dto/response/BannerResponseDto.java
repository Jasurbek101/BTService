package uz.BTService.btservice.controller.dto.response;

import jakarta.persistence.*;
import lombok.*;
import uz.BTService.btservice.entity.AttachEntity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerResponseDto {

    private Integer id;

    private String name;

    private int position;

    private AttachResponseDto attach;

}
