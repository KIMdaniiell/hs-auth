package ru.itmo.hsauth.model.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.hsauth.model.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    @NotNull(message = "role field can't be null")
    private Role role;
}
