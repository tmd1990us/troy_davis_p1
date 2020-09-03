package com.revature.models.controller;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Optional;

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



        String username = req.getParameter("username");
        String password = req.getParameter("password");




        //will need to interact with the database to validate usernames and passwords
        try {
            Optional<User> logInUser =  userRepo.getAUserByUsernameAndPassword(req.getParameter("username"),req.getParameter("password"));

            Role loggedInRole = logInUser.get().getUserRole();
            if(logInUser.isPresent()){
                req.getSession().setAttribute("loggedusername", username);
                req.getSession().setAttribute("loggedpassword",password);
                req.getSession().setAttribute("loggedrole", logInUser.get().getUserRole());
                req.getSession().setAttribute("loggeruser",logInUser);
                System.out.println(logInUser.toString());
                if (loggedInRole == Role.EMPLOYEE){
                    System.out.println("sending to EmployeeDash from Login Controller");
                    return "/api/employeeDash";
                } else if (loggedInRole == Role.ADMIN){
                    System.out.println("sending to AdminDash from Login Controller");
                    //TODO: IMPLEMENT ADMINdash
                    return "/api/home";
                } else if (loggedInRole == Role.FINANCE_MANAGER){
                    System.out.println("sending to FinManDash from Login Controller");
                    //TODO: IMPLEMENT finmandash
                    return "/api/home";
                }

                System.out.println(logInUser);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "/api/wrongcreds";

    }
}
