package br.dev.hygino.springsecurity.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.dev.hygino.springsecurity.dto.CreateUserDto;
import br.dev.hygino.springsecurity.dto.ResponseUserDto;
import br.dev.hygino.springsecurity.entities.Role;
import br.dev.hygino.springsecurity.entities.User;
import br.dev.hygino.springsecurity.repositories.RoleRepository;
import br.dev.hygino.springsecurity.repositories.UserRepository;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ResponseUserDto> newUser(@RequestBody CreateUserDto dto) {
        final var basicRole = roleRepository.findByName(Role.Values.BASIC.name().toUpperCase());
        final var userFromDb = userRepository.findByUsername(dto.username());

        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final var encodedPassword = passwordEncoder.encode(dto.password());
        var user = new User(dto.username(), encodedPassword, Set.of(basicRole));

        user = userRepository.save(user);

        return ResponseEntity.status(201).body(new ResponseUserDto(user));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<ResponseUserDto>> findAll() {
        final var users = userRepository.findAll();

        return ResponseEntity.ok(
                users.stream()
                        .map(ResponseUserDto::new).toList());
    }
}
