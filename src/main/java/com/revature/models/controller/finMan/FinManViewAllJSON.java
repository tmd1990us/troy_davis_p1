package com.revature.models.controller.finMan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.repositories.ReimbursementsRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.Set;

public class FinManViewAllJSON {

    public static void viewallbyStatus(HttpServletRequest req, HttpServletResponse resp){
        ReimbursementsRepository reimbRepo = new ReimbursementsRepository();
        try {

            PrintWriter out = resp.getWriter();
            Set<Reimbursement> reimbs  = reimbRepo.getAllReimbSetByStatus(ReimbursementStatus.getByName(req.getParameter("status")));
            req.getSession().setAttribute("requests",reimbs);


            resp.getWriter().write(new ObjectMapper().writeValueAsString(reimbs));

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewAll(HttpServletRequest req, HttpServletResponse resp) {
        ReimbursementsRepository reimbRepo = new ReimbursementsRepository();

        try {

            PrintWriter out = resp.getWriter();
            Set<Reimbursement> reimbs  = reimbRepo.getAllReimbursements();
            req.getSession().setAttribute("requests",reimbs);


            resp.getWriter().write(new ObjectMapper().writeValueAsString(reimbs));

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
