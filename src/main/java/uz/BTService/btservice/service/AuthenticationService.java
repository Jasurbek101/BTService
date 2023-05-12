package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.BTService.btservice.common.util.DateUtil;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.config.token.JwtService;
import uz.BTService.btservice.dto.LoginRequestDto;
import uz.BTService.btservice.dto.TokenResponseDto;
import uz.BTService.btservice.dto.UserDto;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.entity.role.PermissionEnum;
import uz.BTService.btservice.entity.role.RoleEnum;
import uz.BTService.btservice.entity.role.RolePermissionEntity;
import uz.BTService.btservice.repository.UserRepository;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final Logger log = LoggerFactory.getLogger(getClass().getName());


    public TokenResponseDto register(UserDto request) {
        String jwtToken = jwtService.generateToken(saveUser(request));
        return TokenResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public TokenResponseDto authenticate(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserEntity user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Not found!"));
        String jwtToken = jwtService.generateToken(user);
        return TokenResponseDto.builder()
                .token(jwtToken)
                .allowedRoleEnums(Arrays.asList(RoleEnum.values()))
                .build();
    }

    public UserEntity saveUser(UserDto userDto) {
        Optional<UserEntity> byUsername = userRepository.findByUsernameOriginalDB(userDto.getUsername(),userDto.getPhoneNumber());
        if (byUsername.isPresent()) {
            String username = byUsername.get().getUsername();
            String phoneNumber = byUsername.get().getPhoneNumber();

            if (userDto.getUsername().equals(username))
                throw new RuntimeException(username + " there is a user with this username");
            else if (userDto.getPhoneNumber().equals(phoneNumber)) {
                throw new RuntimeException(phoneNumber + " there is a user with this username");
            }

        }
        UserEntity user = userDto.toEntity("password", "role", "birtDate");
        try {

            if (SecurityUtils.getUsername()!=null) {
                var userEntity = userRepository.findByUsername(SecurityUtils.getUsername()).orElseThrow(() -> new UsernameNotFoundException(" user name not found!"));
               if(userEntity.getRoleEnum().equals(RoleEnum.SUPER_ADMIN)){
                   user.forCreate(userEntity.getId());
               }
            } else user.forCreate();

            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setBirtDate(DateUtils.parseDate(userDto.getBirtDate(), DateUtil.PATTERN14));
            user.setRoleEnum(userDto.getRoleEnum() == RoleEnum.ADMIN ? RoleEnum.ADMIN : RoleEnum.USER);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return userRepository.save(user);
    }

}
