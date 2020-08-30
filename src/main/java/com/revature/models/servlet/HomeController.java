package com.revature.models.servlet;

import javax.servlet.http.HttpServletRequest;

public class HomeController {
    public static String home (HttpServletRequest req){
        //a tun of business logic can go here
        return  "/html/home.html";
    }
}
