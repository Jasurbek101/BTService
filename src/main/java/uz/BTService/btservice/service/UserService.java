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

    public List<UserInterface> getUserAll() {
      return userRepository.getAllUserInterface();
    }


    public UserEntity getUserInformation(Integer id) {
        return userRepository.getUserInformation(id);
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

    public List<UserEntity> getAdminAll() {

       return userRepository.getAllAdmin();

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
