package com.revature.models.servlet;

import javax.servlet.http.HttpServletRequest;

public class LoginController {
    public static String login(HttpServletRequest req){

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
        if(!(username.equals("cheese") && password.equals("louise"))){
            return "/api/wrongcreds";
        }else{
            /**
             * in our project, we may need to store an entire object role, likes, comments, etc
             */
            req.getSession().setAttribute("loggedusername", username);
            req.getSession().setAttribute("loggedpassword",password);
            return "/api/home";
        }

    }
}
