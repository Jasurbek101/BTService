package uz.BTService.btservice.service;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.BTService.btservice.entity.BannerEntity;
import uz.BTService.btservice.entity.base.BaseServerModifierEntity;
import uz.BTService.btservice.repository.AttachRepository;
import uz.BTService.btservice.repository.BannerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BannerService {

    private final BannerRepository bannerRepository;
    private final AttachService attachService;
    public boolean saveBanner(BannerEntity bannerEntity) {
        Optional<BannerEntity> bannerEntityDB = bannerRepository.findByPosition(bannerEntity.getPosition());
        if (bannerEntityDB.isPresent()) {
            BannerEntity banner = bannerEntityDB.get();
            attachService.deleteById(banner.getAttachId());
            bannerRepository.delete(banner);
            bannerRepository.save(bannerEntity);
            return true;
        }
        bannerRepository.save(bannerEntity);
        return true;
    }


    public BannerEntity getById(Integer id) {
        return bannerRepository.getBannerId(id);
    }

    public List<BannerEntity> getByAll() {
        return bannerRepository.getAllBannerEntity();
    }
}
