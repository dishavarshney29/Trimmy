/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.controller;

import com.github.dishavarshney.trimmy.constants.UrlExpiryUnit;
import com.github.dishavarshney.trimmy.exceptions.InvalidCustomShortUrl;
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
            shortUrlInfo.ifPresent(urlInfo -> model.addAttribute("success", "URL Added : " + Utils.getShortUrl(request, urlInfo.getKey())));
        } catch(InvalidCustomShortUrl e) {
            model.addAttribute("error", "Invalid/Empty URL. " + e.getMessage());
        } catch(Exception e) {
            model.addAttribute("error", "Should be a valid HTTP or HTTPS URL");
        }
        loadDefaults(model);
        return "home";
    }

    @PostMapping("/url/delete/{shortUrlKey}")
    public String deleteURL(@PathVariable String shortUrlKey) {
        urlManagerService.deleteUrlEntity(shortUrlKey);
        return "redirect:/app/home/url/" + shortUrlKey;
    }

    public void loadDefaults(Model model) {
        Users lUser = Utils.getUserPrincipalObject();
        model.addAttribute("user", lUser.getUsername());
        model.addAttribute("apiKey", lUser.getToken());
        model.addAttribute("urlList", urlManagerService.listURLEntity());
    }
}
