package com.revature.models.servlet;

import com.revature.models.controller.*;
import com.revature.models.controller.admin.AdminDashboardController;
import com.revature.models.controller.employee.EmployeeDashboardController;
import com.revature.models.controller.employee.SubmitEmployeeReimbursementController;
import com.revature.models.controller.employee.ViewEmployeeRiembursementController;
import com.revature.models.controller.finMan.FinManDashboardController;
import com.revature.models.controller.finMan.FinManViewAllJSON;
import com.revature.models.controller.finMan.FinManViewAllReimbRequest;
import com.revature.repositories.ReimbursementsRepository;
import com.revature.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestHelper {
    public static String process(HttpServletRequest req, HttpServletResponse resp){
        UserRepository userRepository = new UserRepository();
        ReimbursementsRepository reimbRepo = new ReimbursementsRepository();
        System.out.println("THIS IS THE CURRENT URI ACTIVE: " + req.getRequestURI());
        System.out.println("THIS IS THE CURRENT METHOD: "+req.getMethod());

        switch (req.getRequestURI()){
            case "/ers/api/login":
                System.out.println("in login case");
                //not modularized
                //return "/html/home.html";
                //modularized
                return LoginController.login(req,userRepository);


            case "/ers/api/home":
                System.out.println("in home case");
                //not modularized
                //return "/html/login.html";
                //modularized
                return HomeController.home(req);

            case "/ers/api/employeeDash":
                System.out.println("in Employee Dash Case");

                return EmployeeDashboardController.empDash(req, reimbRepo);

            case "/ers/api/adminDash":
                System.out.println("in Admin Dash Case");
                return AdminDashboardController.adminDash(req, reimbRepo, userRepository);

            case "/ers/api/finManDash":
                System.out.println("in Finance MANAGER Dash Case");
                return FinManDashboardController.finDash(req, reimbRepo);

            case "/ers/api/view_all_reimbursements":
                System.out.println("in view All Reimbursements Case");
                return FinManViewAllReimbRequest.viewAll(req, resp, reimbRepo);

            case "/ers/api/view_all_reimbursements_by_status":
                System.out.println("in view All Reimbursements Case");
                return FinManViewAllReimbRequest.viewAllByStatus(req, resp, reimbRepo);

            case "/ers/api/create_account":
                System.out.println("in create account case");
                return CreateAccountController.login(req,userRepository);

            case "/ers/api/submit_reimbursement_request":
                System.out.println("create reimb case");
                return SubmitEmployeeReimbursementController.home(req,resp, reimbRepo);

//            case "/ers/api/view_employee_reimbursements":
//                System.out.println("view reimb case");
//
//                try {
//                    resp.sendRedirect("/epi/view_employee_reimbursements");
////                    req.getRequestDispatcher("/epi/view_employee_reimbursements").forward(req, resp);
//
//                } catch ( IOException e) {
//                    e.printStackTrace();
//                }
//
//                return "/html/badrequest.html";

            default:
                System.out.println("bad Login Case");
                return "/html/badlogin.html";


        }

        //return "html/home.html";
    }
}
