package com.healthsync.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @NotBlank @Size(max = 100)
    private String nombre;
    @NotBlank @Size(max = 100)
    private String apellidos;
    @NotBlank @Email @Size(max = 150)
    private String email;
    @NotBlank @Size(min = 8, max = 100)
    private String password;
}