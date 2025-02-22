package com.example.baseballcommunitybackend.repository;

import com.example.baseballcommunitybackend.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);
    boolean existsByNickname(String nickname);
    List<User> findAllByNickname(String nickname);

}