package com.revature.models.servlet;

import com.revature.models.User;
import com.revature.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;

public class CreateAccountController {
    public static String login(HttpServletRequest req){

        /**
         * may implement route guarding here for your endpoints
         *
         * ex. may want to make sure, only admins can access admin endpoints
         */

        if(!req.getMethod().equals("POST")){
            return "/html/login.html";
        }

        User newUser = new User();

        newUser.setUsername( req.getParameter("username"));
        newUser.setPassword(req.getParameter("password"));
        newUser.setFirstname(req.getParameter("first_name"));
        newUser.setLastname(req.getParameter("last_name"));
        newUser.setEmail(req.getParameter("email"));

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
