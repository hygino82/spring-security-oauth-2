package br.dev.hygino.springsecurity.controllers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.dev.hygino.springsecurity.dto.CreateUserDto;
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
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {
        final var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
        final var userFromDb = userRepository.findByUsername(dto.username());

        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        final var encodedPassword = passwordEncoder.encode(dto.password());
        final var user = new User(dto.username(), encodedPassword, Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
