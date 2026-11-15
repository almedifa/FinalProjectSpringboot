package com.example.auth_service.service.impl;

import com.example.auth_service.dto.request.AuthRequestDto;
import com.example.auth_service.dto.response.AuthResponseDto;
import com.example.auth_service.entity.UserEntity;
import com.example.auth_service.exception.UnauthorizedException;
import com.example.auth_service.exception.UserAllReadyExistException;
import com.example.auth_service.repository.UserRepository;
import com.example.auth_service.service.AuthService;
import com.example.auth_service.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public String register(AuthRequestDto authRequestDto) {
        if (userRepository.existsByUsername(authRequestDto.getUsername())) {
            throw new UserAllReadyExistException(authRequestDto.getUsername());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(authRequestDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(authRequestDto.getPassword()));
        userRepository.save(userEntity);


        return "user " + userEntity.getUsername() + " has been registered successful";
    }

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        UserEntity userEntity = userRepository.findByUsername(authRequestDto.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Username atau password salah"));

        if(!passwordEncoder.matches(authRequestDto.getPassword(), userEntity.getPassword())){
            throw new UnauthorizedException("Username atau password salah");
        }

        String token = jwtUtil.generateToken(userEntity.getUsername());

        return new AuthResponseDto(userEntity.getUsername(), token);


    }
}