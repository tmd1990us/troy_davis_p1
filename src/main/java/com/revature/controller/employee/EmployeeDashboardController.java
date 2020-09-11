package com.revature.controller.employee;

import com.revature.repositories.ReimbursementsRepository;

import javax.servlet.http.HttpServletRequest;

public class EmployeeDashboardController {
    public static String empDash(HttpServletRequest req, ReimbursementsRepository reimbRepo){

        return "/html/employee/employee_dashboard.html";
    }
}
