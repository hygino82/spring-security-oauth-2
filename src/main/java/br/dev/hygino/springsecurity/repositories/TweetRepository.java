package br.dev.hygino.springsecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.hygino.springsecurity.entities.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
