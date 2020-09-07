package com.revature.models.controller;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.util.ConnectionFactory;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateAccountController {
    //TODO: SEPARATE USER REPO INSTANTIATION


    public static String login(HttpServletRequest req, UserRepository userRepo){

        /**
         * may implement route guarding here for your endpoints
         *
         * ex. may want to make sure, only admins can access admin endpoints
         */

        if(!req.getMethod().equals("POST")){
            return "/html/create_account.html";
        }

        User newUser = new User();

        newUser.setUsername( req.getParameter("username"));
        newUser.setPassword(req.getParameter("password"));
        newUser.setFirstname(req.getParameter("first_name"));
        newUser.setLastname(req.getParameter("last_name"));
        newUser.setEmail(req.getParameter("email"));
        //new Users are Employees by default
        newUser.setUserRole(3);

        try {
            if(userRepo.addUser(newUser)){
                req.getSession().setAttribute("loggedusername", newUser.getUsername());
                req.getSession().setAttribute("loggedpassword",newUser.getPassword());
                return "/api/employeeDash";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "/api/home";
    }
}
