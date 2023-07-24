/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.repositories;

import com.github.dishavarshney.trimmy.entity.URLEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Disha Varshney
 */
@Repository
public interface URLRepository extends MongoRepository<URLEntity, String> {

    public List<URLEntity> findByUrlHashCodeAndCreatedBy(Integer hashcode, String createdBy);

    public List<URLEntity> findByCreatedBy(String createdBy);

    public Optional<URLEntity> findByShortenurl(String shortenurl);

    public List<URLEntity> findByExpirationDateBefore(Date currentDate);

}
