package br.dev.hygino.springsecurity.dto;

import java.util.Set;
import java.util.UUID;

import br.dev.hygino.springsecurity.entities.Role;
import br.dev.hygino.springsecurity.entities.User;

public record ResponseUserDto(
        UUID userId,
        String username,
        String password,

        Set<Role> roles) {

    public ResponseUserDto(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles());
    }
}
