package com.github.dishavarshney.trimmy.controller;

import com.github.dishavarshney.trimmy.controller.service.LoginService;
import com.github.dishavarshney.trimmy.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Disha Varshney
 */
@Controller
@RequestMapping("/app")
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping()
    public String defaultPage(Model model) {
        return "redirect:/app/home";
    }

    @GetMapping({"/login", "/register"})
    public String loginPage(@RequestParam(required = false, defaultValue = "") String error, @RequestParam(required = false, defaultValue = "") String logout, Model model) {
        model.addAttribute("user", new Users());
        model.addAttribute("newUser", new Users());
        if (error.equals("loginError")) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout.equals("true")) {
            model.addAttribute("logout", "You have been logged out.");
            model.addAttribute("logout_flag", true);
        } else {
            model.addAttribute("logout_flag", false);
        }
        return "login";
    }

    @PostMapping("/oauthlogin")
    @ResponseBody
    public String login(@RequestBody MultiValueMap<String, String> formData) {
        if (loginService.authenticateOAuthUser(formData.get("Eu").get(0), formData.get("bV").get(0))) {
            return "/app/home";
        } else {
            return "/app/login?error=loginError";
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Users user, Model model) {
        boolean created = false;
        try {
            created = loginService.registerNewUser(user.getUsername(), user.getPassword(), "");
            if (created) {
                model.addAttribute("success", "Registered Sucessfully");
            } else {
                model.addAttribute("error", "Registration Failed: Username already exists");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Registration Failed: Username should be a valid Email");
        }
        return "login";
    }
}
