package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import uz.BTService.btservice.dto.UserDto;
import uz.BTService.btservice.dto.response.FilterForm;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.interfaces.UserInterface;
import uz.BTService.btservice.repository.UserRepository;

import java.text.ParseException;
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
    public Boolean updateUser(UserDto userDto) {
        UserEntity user = userRepository.findByUsername(
                SecurityUtils.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("curren user username not found!")
        );

        log.atInfo().log("!Обновление... пользователя");
        user.forUpdate();
        if (!StringUtils.isEmpty(userDto.getFirstname())) user.setFirstname(userDto.getFirstname());
        if (!StringUtils.isEmpty(userDto.getLastname())) user.setLastname(userDto.getLastname());
        if (!StringUtils.isEmpty(userDto.getUsername())) user.setUsername(userDto.getUsername());
        if (!StringUtils.isEmpty(userDto.getPhoneNumber())) user.setPhoneNumber(userDto.getPhoneNumber());
        if (!StringUtils.isEmpty(userDto.getMiddleName())) user.setMiddleName(userDto.getMiddleName());
        if (!StringUtils.isEmpty(userDto.getPassword()))
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (!StringUtils.isEmpty(userDto.getBirtDate())) {
            try {
                user.setBirtDate(DateUtils.parseDate(userDto.getBirtDate(), DateUtil.PATTERN3));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        userRepository.save(user);
        return true;
    }

    @Transactional
    public Boolean userDelete(Integer id) {
        Integer userDeleteIsSuccess = userRepository.userDelete(id);
        return userDeleteIsSuccess > 0;
    }

    public List<UserDto> getAdminAll() {
        List<UserDto> userResponseDto = new ArrayList<>();
        List<UserEntity> listAdmin = userRepository.getAllAdmin();
        for (UserEntity admin : listAdmin) {
            UserDto addAdmin = admin.toDto("password", "birtDate");
            addAdmin.setBirtDate(String.valueOf(admin.getBirtDate()));
            userResponseDto.add(addAdmin);
        }
        return userResponseDto;
    }

    public UserDto getAdminInformation(Integer id) {
        return userRepository.getAdminById(id).toDto();
    }

    public Sort orderSortField(String field) {
        return Sort.by(Sort.Order.by(field));
    }

    public Pageable pageable(Sort sort, FilterForm filterForm) {
        return PageRequest.of(filterForm.getStart() / filterForm.getLength(), filterForm.getLength(), sort);
    }
}
