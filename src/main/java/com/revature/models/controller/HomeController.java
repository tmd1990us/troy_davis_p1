package com.revature.models.controller;

import javax.servlet.http.HttpServletRequest;

public class HomeController {
    public static String home (HttpServletRequest req){

        Integer loggedInRole = (Integer) req.getSession().getAttribute("loggedrole");
        System.out.println("Logged in role is"+loggedInRole);

        if (loggedInRole == 3){
            System.out.println("sending to EmployeeDash from Login Controller");
            return "/api/employeeDash";
        } else if (loggedInRole == 1){
            System.out.println("sending to AdminDash from Login Controller");
            //TODO: IMPLEMENT ADMINdash
            return "/api/adminDash";
        } else if (loggedInRole == 2){
            System.out.println("sending to FinManDash from Login Controller");
            //TODO: IMPLEMENT finmandash
            return "/api/finManDash";
        }

        System.out.println("could not determine Role in home Controller");
        return "/";

    }
}
