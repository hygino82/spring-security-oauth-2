package br.dev.hygino.springsecurity.dto;

public record LoginResponseDto(
                String accessToken,
                Long expiresIn) {

}
