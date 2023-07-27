///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.github.dishavarshney.trimmy.models;
//
//import com.github.dishavarshney.trimmy.utils.Utils;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//import javax.persistence.Id;
//import javax.persistence.Version;
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.constraints.Size;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.time.Instant;
//import java.util.Date;
//import java.util.HashMap;
//
///**
// * @author Disha Varshney
// */
//@Builder
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Document(collection = "urls")
//public class URLEntity implements PrePersistListener, PreUpdateListener {
//
//    @Id
//    private String id;
//
//    @Size(max = 2147483647)
//    @Field(name = "url")
//    private String url;
//
//    @Field(name = "urlhashcode")
//    private Integer urlHashCode;
//
//    @Size(max = 2147483647)
//    @Field(name = "shortenurl")
//    private String shortenurl;
//
//    @Size(max = 2147483647)
//    @Field(name = "active")
//    private String active;
//
//    @Size(max = 2147483647)
//    @Field(name = "createdby")
//    private String createdBy;
//
//    @Size(max = 2147483647)
//    @Field(name = "updatedby")
//    private String updatedBy;
//
//    @Field(name = "createdat")
//    private Date createdAt;
//
//    @Field(name = "updatedat")
//    private Date updatedAt;
//
//    @Field(name = "expirationdate")
//    private Date expirationDate;
//
//    @Version
//    private Integer version;
//
//    @Override
//    public void onPrePersist() {
//        active = "Y";
//        createdBy = Utils.getUserPrincipal();
//        createdAt = Timestamp.from(Instant.now());
//    }
//
//    @Override
//    public void onPreUpdate() {
//        updatedBy = Utils.getUserPrincipal();
//        updatedAt = Timestamp.from(Instant.now());
//    }
//
//    public HashMap getReturn(HttpServletRequest request) {
//        HashMap lHashMap = new HashMap();
//        lHashMap.put("url", url);
//        lHashMap.put("shorentUrl", Utils.getShortUrl(request, shortenurl));
//        lHashMap.put("user", createdBy);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        String formattedDate = dateFormat.format(expirationDate);
//        lHashMap.put("expiry", formattedDate);
//        return lHashMap;
//    }
//}
