/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.repositories;

import com.github.dishavarshney.trimmy.constants.UrlStatus;
import com.github.dishavarshney.trimmy.models.url.URLDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Disha Varshney
 */
@Repository
public interface URLRepository extends MongoRepository<URLDocument, String>, CustomUrlRepository {
    public List<URLDocument> findByCreatedBy(String createdBy);

    public Optional<URLDocument> findOneByShortUrlKeyAndStatus(String shortUrlKey, UrlStatus status);
}
