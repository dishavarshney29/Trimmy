/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.repositories;

import com.github.dishavarshney.trimmy.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author Disha Varshney
 */
@Repository
public interface UserRepository extends MongoRepository<Users, String> {

    public Optional<Users> findByUsername(String username);

    public Optional<Users> findByToken(String token);
}
