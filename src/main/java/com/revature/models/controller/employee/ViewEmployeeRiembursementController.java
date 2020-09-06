package com.revature.models.controller.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.repositories.ReimbursementsRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ViewEmployeeRiembursementController {
    public static String home(HttpServletRequest req, HttpServletResponse resp, ReimbursementsRepository reimbRepo) {
        try {
            PrintWriter out = resp.getWriter();
            Set<Reimbursement> reimbs  = reimbRepo.getAllReimbSetByAuthorId((Integer) req.getSession().getAttribute("id"));
            req.setAttribute("requests",reimbs);

            for (Reimbursement r : reimbs){
                System.out.println(r);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }



        return "/html/employee/view_employee_reimbursements.html";
    }
}
