/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.controller;

import com.github.dishavarshney.trimmy.service.interfaces.URLManagerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author Disha Varshney
 */
@RestController("/")
public class DefaultController {

    @Autowired
    private URLManagerService urlManagerService;

    private static final Logger LOG = LogManager.getLogger(DefaultController.class);

    @GetMapping
    public String landingPage(Model model) {
        return "redirect:/app/home";
    }

    @RequestMapping("swagger")
    public String home() {
        return "redirect:/swagger-ui.html";
    }

    @Async
    @GetMapping("r/{shortUrlKey}")
    public CompletableFuture<ModelAndView> redirect(@PathVariable("shortUrlKey") final String shortUrlKey) {
        LOG.info("Redirection request for Short URL Key: {}", shortUrlKey);
        String lReturn = "redirect:/app/home"; //"redirect:/notfound"
        try {
            Optional<String> originalUrlOptional = urlManagerService.retrieveOriginalUrl(shortUrlKey);
            if (originalUrlOptional.isPresent() && StringUtils.hasText(originalUrlOptional.get())) {
                LOG.info("Redirecting to {}", originalUrlOptional.get());
                lReturn = "redirect:" + originalUrlOptional.get();
                return CompletableFuture.completedFuture(new ModelAndView(lReturn));
            }
        } catch (Exception e) {
            LOG.error("Error while redirecting", e);
        }
        return CompletableFuture.completedFuture(new ModelAndView(lReturn));
    }
}
