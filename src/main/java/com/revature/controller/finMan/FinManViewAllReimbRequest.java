package com.revature.controller.finMan;

import com.revature.repositories.ReimbursementsRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FinManViewAllReimbRequest {
    public static String viewAll(HttpServletRequest req, HttpServletResponse resp, ReimbursementsRepository reimbRepo) {



        return "/html/fin_manager/view_all_reimbursements.html";
    }

    public static String viewAllByStatus(HttpServletRequest req, HttpServletResponse resp, ReimbursementsRepository reimbRepo) {



        return "/html/fin_manager/view_all_reimbursements_by_status.html";
    }

}
