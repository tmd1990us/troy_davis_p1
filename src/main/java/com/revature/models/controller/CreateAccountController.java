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
        newUser.setUserRole(Role.EMPLOYEE);

        try {
            if(userRepo.addUser(newUser)){
                req.getSession().setAttribute("loggedusername", newUser.getUsername());
                req.getSession().setAttribute("loggedpassword",newUser.getPassword());
                return "/api/employeeDash";
            }else {
                return "/api/wrongcreds";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //will need to interact with the database to validate usernames and passwords
//        if(!(username.equals("cheese") && password.equals("louise"))){
//            return "/api/wrongcreds";
//        }else{
//            /**
//             * in our project, we may need to store an entire object role, likes, comments, etc
//             */
//            req.getSession().setAttribute("loggedusername", username);
//            req.getSession().setAttribute("loggedpassword",password);
//            return "/api/home";
//        }

        return "/api/home";
    }
}
