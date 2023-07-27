/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.utils;

import com.github.dishavarshney.trimmy.models.Users;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Disha Varshney
 */
public class Utils {

    public static String getUserPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public static Users getUserPrincipalObject() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (Users) principal;
        } else {
            return null;
        }
    }

    public static String getShortUrl(HttpServletRequest request, String code) {
        String fullURL = request.getRequestURL().toString();
        String servletPath = request.getServletPath().toString();
        return fullURL.replace(servletPath, "/r/" + code);
    }
}
