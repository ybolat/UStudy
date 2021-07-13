package kz.edu.controller;

import kz.edu.dao.UserDAO;
import kz.edu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Controller2
{
    private final UserDAO userDAO;

    @Autowired
    public Controller2(UserDAO userDAO) { this.userDAO = userDAO;}
    PasswordEncoder passwordEncoder;

    @Autowired
    public void PasswordEncoder(PasswordEncoder passwordEncoder) { this.passwordEncoder = passwordEncoder;}

    @RequestMapping(value={"", "/", "home"})
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userDAO.findByUserName(currentPrincipalName);
        model.addAttribute("user", user);
        model.addAttribute("null", null);
        return "home";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/registration")
    public String registration()
    {
        return "signup";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("password") String password,
                          @RequestParam("login") String login, Model model,
                          @RequestParam("email") String email) {
        System.out.println("REGISTRATION:"+login);
        if (userDAO.findByUserName(login) != null) {
            model.addAttribute("message", "User exists!");
            return "signup";
        }
        else {
            User user = new User();
            user.setLogin(login);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            userDAO.addUser(user);
            return "redirect:/login";
        }
    }
}