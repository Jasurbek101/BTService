package uz.BTService.btservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.BTService.btservice.entity.BannerEntity;

import java.util.List;
import java.util.Optional;

public interface BannerRepository extends JpaRepository<BannerEntity, Integer> {
        @Query(value = "SELECT btsb.* FROM bts_banner btsb WHERE btsb.position=:position", nativeQuery = true)
        Optional<BannerEntity> findByPosition(@Param("position") Integer position);

    @Query(value = "SELECT btsb.* FROM bts_banner btsb ", nativeQuery = true)
    List<BannerEntity> getAllBannerEntity();

    @Query(value = "SELECT btsb.* FROM bts_banner btsb WHERE btsb.id=:id", nativeQuery = true)
    BannerEntity getBannerId(@Param("id") Integer id);
}
