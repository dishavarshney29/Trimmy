/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.service;

import com.github.dishavarshney.trimmy.entity.URLEntity;
import com.github.dishavarshney.trimmy.entity.Users;
import com.github.dishavarshney.trimmy.repositories.URLRepository;
import com.github.dishavarshney.trimmy.repositories.UserRepository;
import com.github.dishavarshney.trimmy.utils.MyEncoder;
import com.github.dishavarshney.trimmy.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author Disha Varshney
 */
@Service
public class URLService {

    @Autowired
    URLRepository uRLRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MyEncoder encoder;

    private static final int EXPIRATION_IN_DAYS = 365;
    private static final int NEXT_SCHEDULE_TIME_IN_HOURS = 24;

    public boolean addURLEntity(URLEntity entity) {
        if (ObjectUtils.isEmpty(entity.getUrl())) {
            return false;
        }
        int hashCode = entity.getUrl().hashCode();
        int encodingHashCode = entity.getUrl().hashCode();
        boolean addRandom = false;
        entity.setUrlHashCode(hashCode);
        Users lUser = Utils.getUserPrincipalObject();
        if (lUser == null) {
            lUser = userRepository.findByToken(Utils.getUserPrincipal()).get();
        }
        List<URLEntity> lURL = uRLRepository.findByUrlHashCodeAndCreatedBy(hashCode, lUser.getUsername());
        if (!lURL.isEmpty()) {
            addRandom = true;
            for (URLEntity uRLEntity : lURL) {
                if (uRLEntity.getUrl().equals(entity.getUrl())) {
                    return false;
                }
            }
        }
        entity.setShortenurl(encoder.encode(encodingHashCode, addRandom));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, EXPIRATION_IN_DAYS);
        entity.setExpirationDate(calendar.getTime());

        uRLRepository.save(entity);
        return true;
    }

    private void deleteExpiredURLs() {
        Date currentDate = new Date();
        List<URLEntity> expiredURLs = uRLRepository.findByExpirationDateBefore(currentDate);
        uRLRepository.deleteAll(expiredURLs);
    }

    public void scheduleDeleteExpiredURLsTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::deleteExpiredURLs, 0, NEXT_SCHEDULE_TIME_IN_HOURS * 60 * 60, TimeUnit.SECONDS);
    }

    public boolean deleteURLEntity(String id) {
        uRLRepository.deleteById(id);
        return true;
    }

    public Optional<URLEntity> getURLEntity(String key) {
        return uRLRepository.findByShortenurl(key);
    }

    public List<URLEntity> ListURLEntity() {
        List<URLEntity> lURL = uRLRepository.findByCreatedBy(Utils.getUserPrincipal());
        return lURL;
    }
}
