package uz.BTService.btservice.controller.dto.response;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseForUserDto{
    private String name;

    private Double price;

    private String color;

    private Integer categoryId;

    private List<AttachResponseDto> attach;

    private String description;

}
