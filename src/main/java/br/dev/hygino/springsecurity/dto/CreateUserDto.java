package br.dev.hygino.springsecurity.dto;

public record CreateUserDto(
        String username,
        String password) {
}
