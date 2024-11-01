package com.seokho.crash.service;

import com.seokho.crash.exception.user.UserNotFoundException;
import com.seokho.crash.exception.user.user.UserAlreadyExistsException;
import com.seokho.crash.model.entity.UserEntity;
import com.seokho.crash.model.repository.UserEntityRepository;
import com.seokho.crash.model.user.User;
import com.seokho.crash.model.user.UserAuthenticationResponse;
import com.seokho.crash.model.user.UserLoginRequestBody;
import com.seokho.crash.model.user.UserSignUpRequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @Autowired private  JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserEntityByUsername(username);
    }

    public User signUp(UserSignUpRequestBody userSignUpRequestBody) {
        userEntityRepository.findByUsername(userSignUpRequestBody.username())
                .ifPresent(
                        user -> {
                            throw new UserAlreadyExistsException();
                        });

        var userEntity = userEntityRepository.save(
                UserEntity.of(
                        userSignUpRequestBody.username(),
                        passwordEncoder.encode(userSignUpRequestBody.password()),
                        userSignUpRequestBody.name(),
                        userSignUpRequestBody.email()));

        return User.from(userEntity);

    }

    public UserAuthenticationResponse authenticate(UserLoginRequestBody userLoginRequestBody) {
        var userEntity =  getUserEntityByUsername(userLoginRequestBody.username()); //유저찾기

        if (passwordEncoder.matches(userLoginRequestBody.password(), userEntity.getPassword())){
            var accessToken =  jwtService.generateAccessToken(userEntity);
            return new UserAuthenticationResponse(accessToken);

        }else {
            throw new UserNotFoundException();
        }

    }

    private UserEntity getUserEntityByUsername(String username) {
        return userEntityRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username)) ;
    }
}
