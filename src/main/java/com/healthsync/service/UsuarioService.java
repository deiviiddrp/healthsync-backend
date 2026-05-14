package com.healthsync.service;

import com.healthsync.dto.RegisterRequestDto;
import com.healthsync.dto.UsuarioResponseDto;
import com.healthsync.exception.BadRequestException;
import com.healthsync.model.Usuario;
import com.healthsync.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDto register(RegisterRequestDto dto) {
        if (usuarioRepo.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }
        Usuario usuario = Usuario.builder()
            .nombre(dto.getNombre())
            .apellidos(dto.getApellidos())
            .email(dto.getEmail())
            .passwordHash(passwordEncoder.encode(dto.getPassword()))
            .activo(true)
            .build();
        usuario = usuarioRepo.save(usuario);
        return UsuarioResponseDto.builder()
            .id(usuario.getId())
            .nombre(usuario.getNombre())
            .apellidos(usuario.getApellidos())
            .email(usuario.getEmail())
            .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Usuario no encontrado: " + email));
        return User.builder()
            .username(usuario.getEmail())
            .password(usuario.getPasswordHash())
            .roles("USER")
            .build();
    }
}