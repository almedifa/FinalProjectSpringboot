package com.example.auth_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequestDto {
    @NotBlank(message =  "username wajib diisi")
    public String username;

    @NotBlank(message =  "Password wajib diisi")
    @Size(min = 8,message =  "Password minimal 8 character" )
    public String password;

}
