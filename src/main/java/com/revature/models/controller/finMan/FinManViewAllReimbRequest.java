package com.revature.models.controller.finMan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.repositories.ReimbursementsRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

public class FinManViewAllReimbRequest {
    public static String viewAll(HttpServletRequest req, HttpServletResponse resp, ReimbursementsRepository reimbRepo) {



        return "/html/fin_manager/view_all_reimbursements.html";
    }

    public static String viewAllByStatus(HttpServletRequest req, HttpServletResponse resp, ReimbursementsRepository reimbRepo) {



        return "/html/fin_manager/view_all_reimbursements_by_status.html";
    }

}
