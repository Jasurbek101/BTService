package uz.BTService.btservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.BTService.btservice.entity.BannerEntity;
import uz.BTService.btservice.repository.BannerRepository;

@Service
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    public static boolean saveBanner(BannerEntity bannerEntity) {
        return false;
    }
}
