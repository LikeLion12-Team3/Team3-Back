package com.likelion.byuldajul.user.repository;

import com.likelion.byuldajul.user.entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {

    Optional<Token> findByEmail(String email);
}
