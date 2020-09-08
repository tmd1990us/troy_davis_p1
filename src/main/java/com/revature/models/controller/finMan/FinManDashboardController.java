package com.revature.models.controller.finMan;

import com.revature.repositories.ReimbursementsRepository;

import javax.servlet.http.HttpServletRequest;

public class FinManDashboardController {
    public static String finDash(HttpServletRequest req, ReimbursementsRepository reimbRepo) {


        return "/html/fin_manager/fin_manager_dashboard.html";
    }
}
