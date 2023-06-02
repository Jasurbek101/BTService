package uz.BTService.btservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true, nullable = false)
    private int position;

    @Column(name = "attach_id")
    private String attachId;

    @ManyToOne
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

//    public UserDto toDto(String... ignoreProperties){
//        return toDto(this, new BannerDto(), ignoreProperties);
//    }

}
