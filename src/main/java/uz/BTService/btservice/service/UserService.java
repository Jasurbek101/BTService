package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.BTService.btservice.common.util.DateUtil;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.controller.dto.UserDto;
import uz.BTService.btservice.controller.dto.dtoUtil.FilterForm;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.interfaces.UserInterface;
import uz.BTService.btservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    public List<UserDto> getUserAll() {
        List<UserDto> userResponseDto = new ArrayList<>();
        List<UserInterface> listUserInterfaces = userRepository.getAllUserInterface();
        log.atInfo().log("!Получение... Все пользователи");

        for (UserInterface uInterface : listUserInterfaces) {
            UserDto dto = new UserDto();
            dto.setId(uInterface.getId());
            dto.setFirstname(uInterface.getFirstname());
            dto.setLastname(uInterface.getLastname());
            dto.setMiddleName(uInterface.getMiddle_name());
            dto.setBirtDate(DateUtil.format(uInterface.getBirth_date(), DateUtil.PATTERN3));
            dto.setPhoneNumber(uInterface.getPhone_number());
            dto.setUsername(uInterface.getUsername());
            dto.setRoleEnum(uInterface.getRole());
            dto.setStatus(uInterface.getStatus());
            userResponseDto.add(dto);
        }
        return userResponseDto;
    }


    public UserDto getUserInformation(Integer id) {
        UserEntity userInformation = userRepository.getUserInformation(id);
//        log.atInfo().log("!Получение... Информация о пользователе по ИД");
//        CitizenInterface regionAndNeighborhood = regionRepository.getRegionAndNeighborhood(userInformation.getRegionId());
        UserDto responseInformationUser = userInformation.toDto();
//        responseInformationUser.setBirtDate(userInformation.getBirtDate()==null ? null :userInformation.getBirtDate().toString());
//        responseInformationUser.setNeighborhoodName(regionAndNeighborhood.getNeighborhood_name());
//        responseInformationUser.setRegionName(regionAndNeighborhood.getRegion_name());
//        UserScoreDto responseUserScore = new UserScoreDto();
//        responseUserScore.setUserScoreDay(userRepository.getUserScoreToday(id));
//        responseUserScore.setUserScoreMonth(userRepository.getUserScoreMonth(id));
//        responseInformationUser.setUserScore(responseUserScore);

        return responseInformationUser;
    }

    @Transactional
    public Boolean updateUser(UserEntity userUpdate) {

        UserEntity userOriginalDB = SecurityUtils.getUser();

        updateUserSave(userUpdate,userOriginalDB);

        return true;
    }

    @Transactional
    public Boolean updateUserById(UserEntity userUpdate, Integer id) {

        UserEntity userOriginalDB = userRepository.getUserId(id).orElseThrow(() ->
                new UsernameNotFoundException("user username not found!")
        );

        updateUserSave(userUpdate,userOriginalDB);

        return true;
    }

    @Transactional
    public Boolean userDelete(Integer id) {
        Integer userDeleteIsSuccess = userRepository.userDelete(id);
        return userDeleteIsSuccess > 0;
    }

    public List<UserDto> getAdminAll() {
        List<UserDto> userResponseDto = new ArrayList<>();
        List<UserEntity> adminList = userRepository.getAllAdmin();
        for (UserEntity admin : adminList) {
            UserDto addAdmin = admin.toDto("password", "birtDate");
            addAdmin.setBirtDate(String.valueOf(admin.getBirtDate()));
            userResponseDto.add(addAdmin);
        }
        return userResponseDto;
    }

    public UserEntity getAdminInformation(Integer id) {
        return userRepository.getAdminById(id);
    }

    private void updateUserSave(UserEntity userUpdate, UserEntity userOriginalDB){
        userVerifyAndSetProperty(userUpdate,userOriginalDB);

        log.atInfo().log("user update");

        userOriginalDB.forUpdate(SecurityUtils.getUserId());

        userRepository.save(userOriginalDB);
    }

    private void userVerifyAndSetProperty(UserEntity userUpdate, UserEntity userOriginalDB){
        if (!StringUtils.isEmpty(userUpdate.getFirstname())) userOriginalDB.setFirstname(userUpdate.getFirstname());
        if (!StringUtils.isEmpty(userUpdate.getLastname())) userOriginalDB.setLastname(userUpdate.getLastname());
        if (!StringUtils.isEmpty(userUpdate.getUsername())) userOriginalDB.setUsername(userUpdate.getUsername());
        if (!StringUtils.isEmpty(userUpdate.getPhoneNumber())) userOriginalDB.setPhoneNumber(userUpdate.getPhoneNumber());
        if (!StringUtils.isEmpty(userUpdate.getMiddleName())) userOriginalDB.setMiddleName(userUpdate.getMiddleName());
        if (!StringUtils.isEmpty(userUpdate.getPassword())) {
            userOriginalDB.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
        if (userUpdate.getBirtDate()!=null) {
            userOriginalDB.setBirtDate(userUpdate.getBirtDate());
        }
    }

    public Sort orderSortField(String field) {
        return Sort.by(Sort.Order.by(field));
    }

    public Pageable pageable(Sort sort, FilterForm filterForm) {
        return PageRequest.of(filterForm.getStart() / filterForm.getLength(), filterForm.getLength(), sort);
    }
}
