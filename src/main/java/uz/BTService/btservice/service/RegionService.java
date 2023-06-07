package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.constants.EntityStatus;
import uz.BTService.btservice.entity.CategoryEntity;
import uz.BTService.btservice.entity.RegionEntity;
import uz.BTService.btservice.exceptions.CategoryNotFoundException;
import uz.BTService.btservice.exceptions.RegionNotFoundException;
import uz.BTService.btservice.repository.RegionRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionEntity addRegion(RegionEntity regionEntity) {
        Integer userId = SecurityUtils.getUserId();
        Optional<RegionEntity> byCreatedByName = regionRepository.findByCreatedByName(regionEntity.getName());
        if (byCreatedByName.isPresent()) {
            return regionStatusCheckAndSave(byCreatedByName, regionEntity, userId);
        }
        regionEntity.forCreate(userId);
        return regionRepository.save(regionEntity);
    }


    private RegionEntity regionStatusCheckAndSave(Optional<RegionEntity> byCreatedByName, RegionEntity regionEntity, Integer userId) {

        RegionEntity regionEntity1 = byCreatedByName.get();
        if (regionEntity1.getStatus() == EntityStatus.DELETED) {

            regionEntity1.setName(regionEntity.getName());

            if (regionEntity.getParentId() != null) {
                regionRepository.findByRegionId(regionEntity1.getParentId()).orElseThrow(() -> {
                    throw new RegionNotFoundException(regionEntity.getParentId() + " parent id not found!");
                });
                regionEntity1.setParentId(regionEntity.getParentId());
            }

            regionEntity1.setStatus(EntityStatus.CREATED);
            regionEntity1.forCreate(userId);
            return regionRepository.save(regionEntity1);
        } else {
            throw new RegionNotFoundException(regionEntity.getName() + " such a region exists!");
        }
    }

    public RegionEntity getRegionIdTree(Integer regionId) {
        if (regionId == null) return null;

        return regionRepository.findById(regionId).orElseThrow(
                () -> {
                    throw new RegionNotFoundException(regionId + "-id not found");
                }
        );
    }

    public RegionEntity getRegionId(Integer regionId) {
        if (regionId == null) return null;

        return regionRepository.getRegionId(regionId).orElseThrow(
                () -> {
                    throw new RegionNotFoundException(regionId + "-id not found!!!");
                }
        );
    }

    public List<RegionEntity> getAllRegion() {
        return regionRepository.findAllRegion();
    }

    public List<RegionEntity> getRegionAllTree() {
        return regionRepository.getRegionAllTree();
    }

    public RegionEntity updateRegion(RegionEntity region) {

        RegionEntity entity = childIdAndParentIdVerify(region);

        entity.setParentId(region.getParentId());
        entity.setName(region.getName());
        entity.forUpdate(SecurityUtils.getUserId());

        return regionRepository.save(entity);
    }

    private RegionEntity childIdAndParentIdVerify(RegionEntity region) {

        RegionEntity entity = null;
        if (region.getParentId() != null) {

            entity = parentIdVerify(region);

        } else {
            entity = regionRepository.findByRegionId(region.getId()).orElseThrow(
                    () -> new RegionNotFoundException(region.getId() + " id not found!!!"));
        }
        return entity;
    }

    private RegionEntity parentIdVerify(RegionEntity region) {

        RegionEntity entity = null;
        List<RegionEntity> parentAndChild = regionRepository.getRegionIdParentAndChild(region.getId(), region.getParentId());

        if (parentAndChild.size() == 2) {
            for (RegionEntity regionDB : parentAndChild) {

                if (Objects.equals(regionDB.getId(), region.getId())) {
                    entity = regionDB;

                }
            }

        } else {
            throw new RegionNotFoundException("id not found!!!");
        }
        return entity;
    }

    @Transactional
    public Boolean delete(Integer id) {
        if (id != null) {
            regionRepository.findByRegionId(id).orElseThrow(
                    () -> new RegionNotFoundException(id + " id not found!!!"));
        }
        regionRepository.regionDelete(id);
        return true;
    }
}
