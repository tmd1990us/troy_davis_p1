package com.revature.models.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementType;
import com.revature.repositories.ReimbursementsRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Set;

@WebServlet("/epi/*")
public class ViewEmployeeReimbursementsServlet extends HttpServlet {
    ReimbursementsRepository reimbRepo = new ReimbursementsRepository();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.getRequestDispatcher(("String").process(req)).forward(req,resp);
        try {
            PrintWriter out = resp.getWriter();
            Set<Reimbursement> reimbs  = reimbRepo.getAllReimbSetByAuthorId(12);
            req.setAttribute("requests",reimbs);

//            resp.getWriter().write(new ObjectMapper().writeValueAsString(reimbs));

            out.println("<html><body>");

            resp.getWriter().write("<table id=\"view_reimbursements_table\" class=\"table\">\n" +
                    "        <tr>\n" +
                    "            <th>ID</th>\n" +
                    "            <th>Amount</th>\n" +
                    "            <th>Submitted </th>\n" +
                    "            <th>Description</th>\n" +
                    "            <th>Type</th>\n" +
                    "            <th>Resolver ID</th>\n" +
                    "            <th>Status</th>\n" +
                    "            <th>Update</th>\n" +
                    "        </tr>\n" +
                    "        \n" +
                    "    ");

            for (Reimbursement r : reimbs){
                resp.getWriter().print("<tr>");

                resp.getWriter().print("<td>");
                resp.getWriter().print(r.getId());
                resp.getWriter().print("</td>");

                resp.getWriter().print("<td>");
                resp.getWriter().print(String.valueOf(r.getAmount()));
                resp.getWriter().print("</td>");

                resp.getWriter().print("<td>");
                resp.getWriter().print(String.valueOf(r.getSubmitted()));
                resp.getWriter().print("</td>");

                resp.getWriter().print("<td>");
                resp.getWriter().print(r.getDescription());
                resp.getWriter().print("</td>");

                resp.getWriter().print("<td>");
                resp.getWriter().print(r.getReimbursementType().toString());
                resp.getWriter().print("</td>");

                resp.getWriter().print("<td>");
                resp.getWriter().print(r.getResolverId());
                resp.getWriter().print("</td>");

                resp.getWriter().print("<td>");
                resp.getWriter().print(String.valueOf(r.getReimbursementStatus()));
                resp.getWriter().print("</td>");

                resp.getWriter().print("<td>");
                resp.getWriter().print(String.valueOf(r.getResolved()));
                resp.getWriter().print("</td>");
                resp.getWriter().print("</tr>");
            }
            resp.getWriter().print("</table>");

            out.println("</html></body>");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        //resp.getWriter().write("in get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().write("in post");

        req.getRequestDispatcher(RequestHelper.process(req, resp)).forward(req,resp);
    }
}
