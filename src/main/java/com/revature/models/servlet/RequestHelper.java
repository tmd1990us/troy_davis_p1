package com.revature.models.servlet;

import com.revature.models.controller.*;
import com.revature.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
    public static String process(HttpServletRequest req){
        UserRepository userRepository = new UserRepository();
        System.out.println("THIS IS THE CURRENT URI ACTIVE " + req.getRequestURI());

        switch (req.getRequestURI()){
            case "/ers/api/login":
                System.out.println("in login case");
                //not modularized
                //return "/html/home.html";
                //modularized
                return LoginController.login(req, userRepository);


//            case "/ers/api/home":
//                System.out.println("in home case");
//                //not modularized
//                //return "/html/login.html";
//                //modularized
//                return HomeController.home(req);

            case "/ers/api/employeeDash":
                System.out.println("in Employee Dash Case");

                return EmployeeDashboardController.empDash();

            case "/ers/api/create_account":
                System.out.println("in create account case");
                return CreateAccountController.login(req, userRepository);


            case "/ers/api/submit_reimbursement_request":
                System.out.println("create reimb case");
                return SubmitReimbursementController.home(req);


            default:
                System.out.println("in login case");
                return "/html/badlogin.html";


        }

        //return "html/home.html";
    }
}
