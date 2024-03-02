package ru.itmo.hsauth.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.hsauth.model.dto.UserDTO;
import ru.itmo.hsauth.model.entity.UserEntity;
import ru.itmo.hsauth.service.util.Mapper;

import java.util.HashSet;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public UserDTO saveUser(UserDTO user) {
        return userService.create(user);
    }

    public String generateToken(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return jwtService.generateToken(userDetails);
    }

    public Claims validate(String token) {
        return jwtService.validateToken(token);
    }

    static class UserMapper implements Mapper<UserEntity, UserDTO> {
        @Override
        public UserDTO entityToDto(UserEntity entity) {
            return new UserDTO(entity.getUserId(), entity.getUsername(), null);
        }

        @Override
        public UserEntity dtoToEntity(UserDTO dto) {
            return new UserEntity(null, dto.getLogin(), dto.getPassword(), new HashSet<>());
        }
    }
}
