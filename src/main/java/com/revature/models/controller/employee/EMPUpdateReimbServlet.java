package com.revature.models.controller.employee;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.repositories.ReimbursementsRepository;
import com.revature.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Set;

@WebServlet("/api/update_employee_reimbursements")
public class EMPUpdateReimbServlet extends HttpServlet {

    UserRepository userRepository = new UserRepository();
    ReimbursementsRepository reimbRepo = new ReimbursementsRepository();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.getRequestDispatcher(("String").process(req)).forward(req,resp);



        try {
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            Set<Reimbursement> reimbs  = reimbRepo.getAllReimbSetByAuthorId((Integer) req.getSession().getAttribute("id"));
            //req.setAttribute("requests",reimbs);


            Enumeration<String> params = req.getParameterNames();
            while(params.hasMoreElements()){
                String paramName = params.nextElement();
                System.out.println("Parameter Name - "+paramName+", Value - "+req.getParameter(paramName));
            }


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }




    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().write("in post");

        int id = Integer.parseInt(req.getParameter("username"));


    }



}
