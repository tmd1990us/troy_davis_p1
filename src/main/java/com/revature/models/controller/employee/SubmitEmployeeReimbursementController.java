package com.revature.models.controller.employee;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementType;
import com.revature.repositories.ReimbursementsRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class SubmitEmployeeReimbursementController {
    public static String home(HttpServletRequest req, HttpServletResponse resp, ReimbursementsRepository reimbRepo){

        if(!req.getMethod().equals("POST")){
            return "/html/employee/submit_reimbursement_request.html";
        }


        Reimbursement temp = new Reimbursement();
        int tempId = (int) req.getSession().getAttribute("id");
        temp.setAuthorId(tempId);

        temp.setAmount(Double.valueOf(req.getParameter("amount")));
        temp.setDescription(req.getParameter("description"));
        temp.setReimbursementType(ReimbursementType.getByName(req.getParameter("type")));
        
        try {
            reimbRepo.addReimbursement(temp);
            System.out.println("added reimb to database: " + temp.toString());
            return "/api/employeeDash";
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        //a tun of business logic can go here
        return  "/api/employeeDash";
    }
}
