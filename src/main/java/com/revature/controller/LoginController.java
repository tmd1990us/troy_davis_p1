package com.revature.controller;

import com.revature.models.User;
import com.revature.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController {
    public static String login(HttpServletRequest req, UserRepository userRepository){

        /**
         * may implement route guarding here for your endpoints
         *
         * ex. may want to make sure, only admins can access admin endpoints
         */

        if(!req.getMethod().equals("POST")){
            return "/html/login.html";
        }



        String username = req.getParameter("username");
        String password = req.getParameter("password");




        //will need to interact with the database to validate usernames and passwords
        try {
            Optional<User> logInUser =  userRepository.getAUserByUsernameAndPassword(req.getParameter("username"),req.getParameter("password"));

            if (logInUser.isPresent()){

                req.getSession().setAttribute("loggedusername", username);
                req.getSession().setAttribute("loggedpassword",password);
                req.getSession().setAttribute("loggedrole", logInUser.get().getUserRole());

                req.getSession().setAttribute("id",logInUser.get().getUserId());

                System.out.println("Logged In User id parameter is: " + logInUser.get().getUserId());

                System.out.println(logInUser.toString());

                System.out.println("sending to home from log in screen");
                return "/api/home";
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/api/wrongcreds";

    }
}
