/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.controller;

import com.github.dishavarshney.trimmy.controller.service.URLService;
import com.github.dishavarshney.trimmy.entity.URLEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Disha Varshney
 */
@RestController
@RequestMapping("/api")
public class RestAPIController {

    @Autowired
    URLService lService;

    @GetMapping("/encode")
    public ResponseEntity encode(@RequestParam String url, HttpServletRequest request) {
        URLEntity lEntity = new URLEntity();
        lEntity.setUrl(url);
        try {
            boolean addURLEntity = lService.addURLEntity(lEntity);
            if (addURLEntity) {
                return ResponseEntity.ok(lEntity.getReturn(request));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Already URL Found / Empty URL");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Should be a valid HTTP or HTTPS URL");
        }
    }

    @GetMapping("/list")
    public ResponseEntity listAll(HttpServletRequest request) {
        List<HashMap> lReturn = new ArrayList<>();
        List<URLEntity> URLEntityList = lService.ListURLEntity();
        if (URLEntityList.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            for (URLEntity uRLEntity : URLEntityList) {
                lReturn.add(uRLEntity.getReturn(request));
            }
            return ResponseEntity.ok(lReturn);
        }
    }
}