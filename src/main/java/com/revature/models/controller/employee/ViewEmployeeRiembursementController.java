package com.revature.models.controller.employee;

import com.revature.models.Reimbursement;
import com.revature.repositories.ReimbursementsRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ViewEmployeeRiembursementController {
    public static String home(HttpServletRequest req, ReimbursementsRepository reimbRepo) {

        try {
            Set<Reimbursement> reimbs  = reimbRepo.getAllReimbSetByAuthorId((Integer) req.getSession().getAttribute("id"));
            req.setAttribute("requests",reimbs);

            for (Reimbursement r : reimbs){
                fgdfg
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return "/html/employee/view_employee_reimbursements.html";
    }
}
