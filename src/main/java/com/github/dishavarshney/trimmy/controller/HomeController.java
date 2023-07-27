/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.controller;

import com.github.dishavarshney.trimmy.constants.UrlExpiryUnit;
import com.github.dishavarshney.trimmy.models.Users;
import com.github.dishavarshney.trimmy.models.url.ShortUrlInfo;
import com.github.dishavarshney.trimmy.models.url.URLDocument;
import com.github.dishavarshney.trimmy.service.interfaces.URLManagerService;
import com.github.dishavarshney.trimmy.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author Disha Varshney
 */
@Controller
@RequestMapping("/app/home")
public class HomeController {

    @Autowired
//    URLService uRLService;
    URLManagerService urlManagerService;

    @GetMapping({"", "/", "/url", "/url/{id}"})
    public String homePage(Model model) {
        loadDefaults(model);
        return "home";
    }

    @PostMapping("/url")
    public String saveURL(@ModelAttribute URLDocument urlDocument, Model model, HttpServletRequest request) {
        try {
            Optional<ShortUrlInfo> shortUrlInfo = urlManagerService.createShortUrlKey(urlDocument.getOriginalUrl(), urlDocument.getCustomShortUrl(), UrlExpiryUnit.YEARS, 1);
            if (shortUrlInfo.isPresent()) {
                model.addAttribute("success", "URL Added : " + Utils.getShortUrl(request, urlDocument.getShortUrlKey()));
            } else {
                model.addAttribute("error", "Already URL Found / Empty URL");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Should be a valid HTTP or HTTPS URL");
        }
        loadDefaults(model);
        return "home";
    }

    @PostMapping("/url/delete/{id}")
    public String deleteURL(@PathVariable String id) {
        urlManagerService.deleteUrlEntity(id);
        return "redirect:/app/home/url/" + id;
    }

    public void loadDefaults(Model model) {
        Users lUser = Utils.getUserPrincipalObject();
        model.addAttribute("user", lUser.getUsername());
        model.addAttribute("apiKey", lUser.getToken());
        model.addAttribute("urlList", urlManagerService.listURLEntity());
    }
}
