package br.dev.hygino.springsecurity.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.hygino.springsecurity.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
