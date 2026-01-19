package br.dev.hygino.springsecurity.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.hygino.springsecurity.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
