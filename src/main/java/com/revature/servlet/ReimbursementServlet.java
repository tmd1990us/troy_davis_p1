package com.revature.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.dtos.ErrorResponse;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

@WebServlet("/reimbursements")
public class ReimbursementServlet extends HttpServlet {
    ReimbursementService reimbService = new ReimbursementService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            String reimbIdParam = req.getParameter("reimbID");
            String statusParam =  req.getParameter("status");
            String typeParam = req.getParameter("type");
            Integer loggedRole = (Integer) req.getSession().getAttribute("loggedinrole");
            if (loggedRole == 3){
                Integer userId = ((Integer) req.getSession().getAttribute("userId"));
                List<Reimbursement> reimbs = reimbService.getReimbByUserId(userId);
                String reimbJSON = mapper.writeValueAsString(reimbs);
                writer.write(reimbJSON);
                resp.setStatus(200);
            } else if (statusParam != null){
                if (((Integer) req.getSession().getAttribute("loggedinrole")) == 2){
                    Set<Reimbursement> reimbs = reimbService.getReimbByStatus(statusParam);
                    String reimbJSON = mapper.writeValueAsString(reimbs);
                    writer.write(reimbJSON);
                    resp.setStatus(200);
                }
            }else if (typeParam != null){
                if (((Integer) req.getSession().getAttribute("loggedinrole")) == 2){
                    Set<Reimbursement> reimbs = reimbService.getReimbByType(typeParam);
                    String reimbJSON = mapper.writeValueAsString(reimbs);
                    writer.write(reimbJSON);
                    resp.setStatus(200);
                }
            } else if (((Integer) req.getSession().getAttribute("loggedinrole")) == 2){
                Set<Reimbursement> reimbs = reimbService.getAllReimb();
                String reimbJSON = mapper.writeValueAsString(reimbs);
                writer.write(reimbJSON);
                resp.setStatus(200);
            }

        } catch (ResourceNotFoundException rnfe){
            resp.setStatus(404);
            ErrorResponse err = new ErrorResponse(404,rnfe.getMessage());
            writer.write(mapper.writeValueAsString(err));
        } catch (NumberFormatException | InvalidRequestException nfe){
            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(404, "Malformed user id");
            writer.write(mapper.writeValueAsString(err));
        } catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500); //500 = internal server error
            ErrorResponse err = new ErrorResponse(500,"server: my bad.");
            writer.write(mapper.writeValueAsString(err));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Reimbursement reimbursement = mapper.readValue(req.getInputStream(),Reimbursement.class);
            reimbursement.setAuthorId((Integer) req.getSession().getAttribute("userId"));
            reimbService.save(reimbursement);
            System.out.println(reimbursement);
            String newReimbJSON = mapper.writeValueAsString(reimbursement);
            writer.write(newReimbJSON);
            resp.setStatus(201);
        } catch (MismatchedInputException upe) {
            upe.printStackTrace();
            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(400,"Bad Request: malformed user object found in request body");
            writer.write(mapper.writeValueAsString(err));
        }catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500); //500 = internal server error
            ErrorResponse err = new ErrorResponse(500,"server: my bad.");
            writer.write(mapper.writeValueAsString(err));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Reimbursement reimbursement = mapper.readValue(req.getInputStream(),Reimbursement.class);
            reimbursement.setAuthorId((Integer) req.getSession().getAttribute("userId"));
            reimbService.updateEMP(reimbursement);
            System.out.println(reimbursement);
            String newReimbJSON = mapper.writeValueAsString(reimbursement);
            writer.write(newReimbJSON);
            resp.setStatus(201);
        } catch (MismatchedInputException upe) {
            upe.printStackTrace();
            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(400,"Bad Request: malformed user object found in request body");
            writer.write(mapper.writeValueAsString(err));
        }catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500); //500 = internal server error
            ErrorResponse err = new ErrorResponse(500,"server: my bad.");
            writer.write(mapper.writeValueAsString(err));
        }
    }
}
