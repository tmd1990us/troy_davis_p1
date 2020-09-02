package com.revature.models.servlet;

import javax.servlet.http.HttpServletRequest;

public class HomeController {
    public static String home (HttpServletRequest req){

        if (req.getSession().getAttribute("loggedrole").equals("Employee")){
            return "/html/employee/employee_dashboard.html";
        } else if (req.getSession().getAttribute("loggedrole").equals("FINANCE_MANAGER")){
            return "/html/fin_manager/dashboard.html";
        }else if (req.getSession().getAttribute("loggedrole").equals("ADMIN")){
            return "/html/fin_manager/dashboard.html";
        }else {
            //a tun of business logic can go here
            return  "/html/home.html";
            //return "/html/employee/employee_dashboard.html";
        }

    }
}
