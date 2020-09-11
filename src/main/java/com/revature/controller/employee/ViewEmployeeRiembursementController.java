package com.revature.controller.employee;

import com.revature.repositories.ReimbursementsRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewEmployeeRiembursementController {
    public static String home(HttpServletRequest req, HttpServletResponse resp, ReimbursementsRepository reimbRepo) {




        return "/html/employee/view_employee_reimbursements.html";
    }

}

