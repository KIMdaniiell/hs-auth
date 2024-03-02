package ru.itmo.hsauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.hsauth.controller.exceptions.already_applied.RoleAlreadyGrantedException;
import ru.itmo.hsauth.controller.exceptions.already_applied.UserAlreadyExistsException;
import ru.itmo.hsauth.controller.exceptions.not_found.NotFoundException;
import ru.itmo.hsauth.controller.exceptions.not_found.UserNotFoundException;
import ru.itmo.hsauth.controller.exceptions.unavailable_action.RoleRestrictedToGrantManuallyException;
import ru.itmo.hsauth.model.dto.RoleDTO;
import ru.itmo.hsauth.model.dto.UserDTO;
import ru.itmo.hsauth.model.entity.Role;
import ru.itmo.hsauth.model.entity.UserEntity;
import ru.itmo.hsauth.repository.UserRepository;
import ru.itmo.hsauth.service.feign.ActorsClientForManagersByUserWrapper;
import ru.itmo.hsauth.service.feign.ActorsClientForManagersDeleteWrapper;
import ru.itmo.hsauth.service.feign.ActorsClientForPlayerByUserWrapper;
import ru.itmo.hsauth.service.feign.ActorsClientForPlayersDeleteWrapper;
import ru.itmo.hsauth.service.util.GeneralService;
import ru.itmo.hsauth.service.util.Mapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService extends GeneralService<UserEntity, UserDTO> implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final Mapper<UserEntity, UserDTO> mapper = new UserMapper();

    private final UserRepository userRepository;
    private final ActorsClientForPlayerByUserWrapper actorsClientForPlayerByUserWrapper;
    private final ActorsClientForPlayersDeleteWrapper actorsClientForPlayersDeleteWrapper;
    private final ActorsClientForManagersByUserWrapper actorsClientForManagersByUserWrapper;
    private final ActorsClientForManagersDeleteWrapper actorsClientForManagersDeleteWrapper;


    @Override
    public UserDTO create(UserDTO dto) {
        if (userRepository.findByUsername(dto.getLogin()).isPresent()) {
            throw new UserAlreadyExistsException(dto.getLogin());
        }

        dto.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );

        return super.create(dto);
    }

    public UserDTO findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new UserNotFoundException("username = " + username));
    }

    @Transactional
    public void addRole(long id, RoleDTO roleDTO, boolean entityExists) {
        Role role = roleDTO.getRole();
        if (isRoleNotAllowedGrantManually(role) && !entityExists) {
            throw new RoleRestrictedToGrantManuallyException(role);
        }
        UserEntity user = getEntityById(id);
        if (user.getRoles().contains(role)) {
            throw new RoleAlreadyGrantedException(id, role);
        }
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional
    public void addRole(long id, RoleDTO roleDTO) {
        addRole(id, roleDTO, false);
    }

    @Transactional
    public void removeRole(long id, RoleDTO roleDTO) {
        removeRole(id, roleDTO, true);
    }

    @Transactional
    public void removeRole(long id, RoleDTO roleDTO, boolean deleteSideEntity) {
        UserEntity user = getEntityById(id);
        Role role = roleDTO.getRole();
        if (deleteSideEntity) {
            switch (role) {
                case ROLE_PLAYER -> {
                    long playerId = actorsClientForPlayerByUserWrapper.findPlayerIdByUserId(id);
                    actorsClientForPlayersDeleteWrapper.deletePlayerById(playerId);
                }
                case ROLE_TEAM_MANAGER -> {
                    long managerId = actorsClientForManagersByUserWrapper.findTeamManagerIdByUserId(id);
                    actorsClientForManagersDeleteWrapper.deleteManagerById(managerId);
                }
            }
        }
        user.getRoles().remove(roleDTO.getRole());
        userRepository.save(user);
    }

    public List<RoleDTO> getRoles(long id) {
        UserEntity user = getEntityById(id);
        return user.getRoles()
                .stream()
                .map(RoleDTO::new)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username)
                .map(entity -> {
                    return new UserDetails() {
                        @Override
                        public Collection<? extends GrantedAuthority> getAuthorities() {
                            return entity.getRoles()
                                    .stream()
                                    .map(role -> new GrantedAuthority() {
                                        @Override
                                        public String getAuthority() {
                                            return role.name();
                                        }
                                    })
                                    .collect(Collectors.toList());
                        }

                        @Override
                        public String getPassword() {
                            return entity.getPassword();
                        }

                        @Override
                        public String getUsername() {
                            return entity.getUsername();
                        }

                        @Override
                        public boolean isAccountNonExpired() {
                            return true;
                        }

                        @Override
                        public boolean isAccountNonLocked() {
                            return true;
                        }

                        @Override
                        public boolean isCredentialsNonExpired() {
                            return true;
                        }

                        @Override
                        public boolean isEnabled() {
                            return true;
                        }
                    };
                })
                .orElseThrow(() -> new AuthenticationException("User `%s` is not registered yet".formatted(username)) {
                });
        return user;
    }


    private boolean isRoleNotAllowedGrantManually(Role role) {
        return role == Role.ROLE_PLAYER || role == Role.ROLE_TEAM_MANAGER;
    }

    @Override
    protected NotFoundException getNotFoundIdException(long id) {
        return new UserNotFoundException("id = " + id);
    }

    @Override
    protected Mapper<UserEntity, UserDTO> getMapper() {
        return mapper;
    }

    @Override
    protected JpaRepository<UserEntity, Long> getRepository() {
        return userRepository;
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
