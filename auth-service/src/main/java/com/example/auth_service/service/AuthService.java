package com.example.auth_service.service;

import com.example.auth_service.dto.request.AuthRequestDto;
import com.example.auth_service.dto.response.AuthResponseDto;

public interface AuthService {
    String register(AuthRequestDto authRequestDto);
    AuthResponseDto login(AuthRequestDto authRequestDto);
}
