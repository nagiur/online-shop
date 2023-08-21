package com.nagiur.onlineshop.web;

import com.nagiur.onlineshop.domain.User;
import com.nagiur.onlineshop.dto.LoginDTO;
import com.nagiur.onlineshop.repository.UserRepositoryImpl;
import com.nagiur.onlineshop.service.UserService;
import com.nagiur.onlineshop.service.UserServiceImpl;
import com.nagiur.onlineshop.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(LoginServlet.class);
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws SecurityException, IOException, ServletException {
        LOGGER.info("Serving login page");
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws SecurityException, IOException, ServletException {
        var loginDTO = new LoginDTO(req.getParameter("username"), req.getParameter("password"));
        LOGGER.info("Received long data: {}", loginDTO);


        var errors = ValidationUtil.getInstance().validate(loginDTO);

        if (!errors.isEmpty()) {
            LOGGER.info("Failed to login, sending login form again");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }

        try {
            login(loginDTO, req);
            LOGGER.info("Login successful, redirecting to home page");
            resp.sendRedirect("/home");
        } catch (UserPrincipalNotFoundException e) {
            LOGGER.error("incorrect username/password", e);
            errors.put("username", "Incorrect username/password");
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }
    }

    private void login(LoginDTO loginDTO, HttpServletRequest req) {
        User user = userService.verifyUser(loginDTO);

        //get the old session and invalidate
        HttpSession oldSession = req.getSession(false);
        if(oldSession != null){
            oldSession.invalidate();
        }

        //put user in the sessions
        HttpSession session = req.getSession(true);
        session.setAttribute("user", user);
    }
}
