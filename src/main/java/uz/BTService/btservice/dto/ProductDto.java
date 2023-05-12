package uz.BTService.btservice.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long categoryEntityId;
    private String name;
    public Double price;
    private Long attachmentId;
    private String description;
}
