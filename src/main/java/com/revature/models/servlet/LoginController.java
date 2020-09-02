package com.revature.models.servlet;

import com.revature.models.User;
import com.revature.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class LoginController {
    public static String login(HttpServletRequest req, UserRepository userRepo){

        /**
         * may implement route guarding here for your endpoints
         *
         * ex. may want to make sure, only admins can access admin endpoints
         */

        if(!req.getMethod().equals("POST")){
            return "/html/login.html";
        }

        User logInUser = new User();

        String username = req.getParameter("username");
        String password = req.getParameter("password");




        //will need to interact with the database to validate usernames and passwords
        try {

            if(userRepo.getAUserByUsernameAndPassword(username,password).isPresent()){
                req.getSession().setAttribute("loggedusername", username);
                req.getSession().setAttribute("loggedpassword",password);
                req.getSession().setAttribute("loggedrole", userRepo.getAUserByUsernameAndPassword(username,password).get().getUserRole());
                System.out.println(logInUser.getUserRole());
                return "/api/home";

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "/api/wrongcreds";

    }
}
