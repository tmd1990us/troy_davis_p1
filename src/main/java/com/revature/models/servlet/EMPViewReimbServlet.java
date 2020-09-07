package com.revature.models.servlet;

import com.revature.models.Reimbursement;
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
import java.util.Set;

@WebServlet("/epi/view_employee_reimbursements")
public class EMPViewReimbServlet extends HttpServlet {

    UserRepository userRepository = new UserRepository();
    ReimbursementsRepository reimbRepo = new ReimbursementsRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.getRequestDispatcher(("String").process(req)).forward(req,resp);

        try {
            PrintWriter out = resp.getWriter();
            Set<Reimbursement> reimbs  = reimbRepo.getAllReimbSetByAuthorId((Integer) req.getSession().getAttribute("id"));
            //req.setAttribute("requests",reimbs);

            out.write(mapReimbToHTMLTable(reimbs));


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //resp.getWriter().write("in post");


    }


    private static String mapReimbToHTMLTable(Set<Reimbursement> reimb){

        String htmlHead = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Employee Reimbursements</title>\n" +
                "\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"/ers/css/ers_styles.css\"/>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "    <div class=\"login-container\">\n" +
                "        <h4>View Your Reimbursements & Details</h4>\n" +
                "    </div>\n" +
                "    </br>\n" +
                "    <table id=\"view_reimbursements_table\" class=\"table\"  >\n" +
                "        <tr>\n" +
                "            <th>ID</th>\n" +
                "            <th>Amount</th>\n" +
                "            <th>Submitted </th>\n" +
                "            <th>Description</th>\n" +
                "            <th>Type</th>\n" +
                "            <th>Resolver ID</th>\n" +
                "            <th>Status</th>\n" +
                "            <th>Update</th>\n" +
                "            <!-- Take them back to submit Reim. with details from the selected reimb.  -->\n" +
                "        </tr>";


        String htmlFoot = "\n" +
                "    </table>\n" +
                "    \n" +
                "<!-- JS, Popper.js, and jQuery -->\n" +
                "<script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js\" integrity=\"sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\" integrity=\"sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV\" crossorigin=\"anonymous\"></script>\n" +
                "\n" +
                "</body>\n" +
                "</html>";



        String returnString = "";
        returnString = returnString.concat(htmlHead);

        for (Reimbursement r : reimb){
            String tr = "<tr>\n" +
                    "            <th>"+ r.getId() + "</th>\n" +
                    "            <th>"+ r.getAmount() +"</th>\n" +
                    "            <th>"+ r.getSubmitted() + "</th>\n" +
                    "            <th>"+ r.getDescription() +"</th>\n" +
                    "            <th>"+ r.getReimbursementType() +"</th>\n" +
                    "            <th>"+ r.getResolverId() +"</th>\n" +
                    "            <th>"+ r.getReimbursementStatus() +"</th>\n" +
                    "            <th>"+ r.getResolved() +"</th>\n" +
                    "            <!-- Take them back to submit Reim. with details from the selected reimb.  -->\n" +
                    "        </tr>";
            returnString = returnString.concat(tr);
        }


        returnString = returnString.concat(htmlFoot);


        return returnString;
    }

}
