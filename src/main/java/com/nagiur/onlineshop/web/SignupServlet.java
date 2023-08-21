package com.nagiur.onlineshop.web;

import com.nagiur.onlineshop.dto.UserDTO;
import com.nagiur.onlineshop.repository.UserRepositoryImpl;
import com.nagiur.onlineshop.service.UserService;
import com.nagiur.onlineshop.service.UserServiceImpl;
import com.nagiur.onlineshop.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final static Logger LOGGER
            = LoggerFactory.getLogger(SignupServlet.class);

    private UserService userService
            = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Serving signup page");

        req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        UserDTO userDTO = copyParametersTo(req);
        var errors = ValidationUtil.getInstance().validate(userDTO);

        if (!errors.isEmpty()) {
            req.setAttribute("userDto", userDTO);
            req.setAttribute("errors", errors);
            LOGGER.info("User sent invalid data: {}", userDTO);
            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
        } else if (userService.isNotUniqueUsername(userDTO)) {
            LOGGER.info("Username: {} is already exist", userDTO.getUsername());
            errors.put("username", "The username already exists. Please use a different username");
            req.setAttribute("userDto", userDTO);
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
        } else if (userService.isNotUniqueEmail(userDTO)) {
            LOGGER.info("Email: {} is already exist", userDTO.getUsername());
            errors.put("email", "The email already exists. Please use a different email");
            req.setAttribute("errors", errors);
            req.setAttribute("userDto", userDTO);
            req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
        } else {
            LOGGER.info("user is valid, creating a new user with: {}", userDTO);
            userService.saveUser(userDTO);
            resp.sendRedirect("/login");
        }
    }

    private UserDTO copyParametersTo(HttpServletRequest req) {
        var userDTO = new UserDTO();
        userDTO.setUsername(req.getParameter("username"));
        userDTO.setFirstName(req.getParameter("firstname"));
        userDTO.setLastName(req.getParameter("lastname"));
        userDTO.setPassword(req.getParameter("password"));
        userDTO.setPasswordConfirmed(req.getParameter("passwordConfirmed"));
        userDTO.setEmail(req.getParameter("email"));

        return userDTO;
    }

}
