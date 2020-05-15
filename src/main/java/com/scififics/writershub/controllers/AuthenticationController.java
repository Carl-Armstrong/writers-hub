package com.scififics.writershub.controllers;

import com.scififics.writershub.data.UserRepository;
import com.scififics.writershub.models.User;
import com.scififics.writershub.models.dto.LoginFormDTO;
import com.scififics.writershub.models.dto.RegisterFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    private static final String userSessionKey = "user";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new RegisterFormDTO());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, HttpServletRequest request,
                                          Model model) {

        // if there are errors, return to the registration page
        if (errors.hasErrors()) {
            return "register";
        }

        // attempt to find the provided username in the database
        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        // if the username was found (!=null), set a custom error and return to registration page
        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", " - That username is not available");
            return "register";
        }

        // check both password fields and verify that they match. If not, return to registration page
        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "register";
        }

        // username and password are valid. Create new User and store it in database. Then, create a session for the user.
        User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getPassword());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new LoginFormDTO());
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO,
                                   Errors errors, HttpServletRequest request, Model model) {

        // if there are errors on the form, return to login page
        if (errors.hasErrors()) {
            return "login";
        }

        // attempt to find the provided username in the database
        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());

        // if the username was not found in the database, set a custom error and return to login page
        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", " - This username does not exist");
            return "login";
        }

        // compare the provided password with the actual password. If they don't match, set an error and return to login
        String password = loginFormDTO.getPassword();
        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", " - Incorrect password");
            return "login";
        }

        // username and password are valid. Create a session for the user
        setUserInSession(request.getSession(), theUser);

        return "redirect:";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
