package com.scififics.writershub;

import com.scififics.writershub.controllers.AuthenticationController;
import com.scififics.writershub.data.UserRepository;
import com.scififics.writershub.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    // list of whitelisted paths (anything beginning with these paths is whitelisted)
    private static final List<String> whitelist = Arrays.asList("/login", "/register", "/logout", "/css", "/home");

    private static boolean isWhitelisted(String path) {
        /*
            The following if statement should be commented out or deleted.
            It whitelists all pages. This is for convenience while developing.
         */
        if (path.startsWith("/")) {
            return true;
        }

        // This whitelists the landing page. - .equals rather than .startsWith
        if (path.equals("/")) {
            return true;
        }

        // This whitelists all paths on the list defined above
        for (String pathRoot : whitelist) {
            if (path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {

        // Don't require sign-in for whitelisted pages
        if (isWhitelisted(request.getRequestURI())) {
            // returning true indicates that the request may proceed
            return true;
        }

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        // The user is logged in
        if (user != null) {
            return true;
        }

        // The user is NOT logged in
        response.sendRedirect("/login");
        return false;
    }
}
