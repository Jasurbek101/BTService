package uz.BTService.btservice.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.BTService.btservice.common.util.SecurityUtils;
import uz.BTService.btservice.config.token.JwtService;
import uz.BTService.btservice.controller.convert.TokenResponseConvert;
import uz.BTService.btservice.controller.dto.request.LoginRequestDto;
import uz.BTService.btservice.controller.dto.response.TokenResponseDto;
import uz.BTService.btservice.entity.UserEntity;
import uz.BTService.btservice.entity.role.RoleEnum;
import uz.BTService.btservice.exceptions.UserDataException;
import uz.BTService.btservice.exceptions.AuthenticationException;
import uz.BTService.btservice.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;


    public TokenResponseDto register(UserEntity request) {

        UserEntity userEntity = saveUser(request);
        String jwtToken = jwtService.generateToken(userEntity);

        return TokenResponseConvert.from(jwtToken,userEntity);

    }

    public TokenResponseDto authenticate(LoginRequestDto request) {

        UserEntity user = verifyUser(request);
        String jwt = jwtService.generateToken(user);

        return TokenResponseConvert.from(jwt,user);
    }

    public UserEntity saveUser(UserEntity user) throws UserDataException {

        Optional<UserEntity> userDB = userRepository.findByUsernameOriginalDB(user.getUsername(), user.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userDB.isPresent()) {
            userPasswordAndPhoneNumberCheck(userDB, user);
        }

        userForCreate(user);

        return userRepository.save(user);
    }

    private void userPasswordAndPhoneNumberCheck(Optional<UserEntity> user, UserEntity userDto) {

        String username = user.get().getUsername();
        String phoneNumber = user.get().getPhoneNumber();

        if (userDto.getUsername().equals(username))
            throw new RuntimeException(username + " there is a user with this username");
        else if (userDto.getPhoneNumber().equals(phoneNumber)) {
            throw new RuntimeException(phoneNumber + " there is a user with this username");
        }

    }

    private void userForCreate(UserEntity user) {
        if (SecurityUtils.getUsername() != null) {
            var userEntity = SecurityUtils.getUser();
            assert userEntity != null;
            if (userEntity.getRoleEnum() == RoleEnum.SUPER_ADMIN) {
                user.forCreate(userEntity.getId());
            }
        } else user.forCreate();
    }

    private UserEntity verifyUser(LoginRequestDto request) {
        UserEntity user = repository.findByUsername(request.getUsername()).orElseThrow(() -> {
            throw new AuthenticationException(request.getUsername() + " username not found!");
        });

        verifyPassword(request, user.getPassword());

        return user;
    }

    private void verifyPassword(LoginRequestDto request, String userPassword) {
        if (!passwordEncoder.matches(request.getPassword(), userPassword)) {
            throw new AuthenticationException("Incorrect password");
        }
    }

}
