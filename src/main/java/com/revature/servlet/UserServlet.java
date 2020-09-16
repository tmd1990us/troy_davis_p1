package com.revature.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.dtos.ErrorResponse;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        System.out.println(req.getRequestURI());
        System.out.println(req.getParameter("id"));
        try {
//            String idParam = req.getParameter("id");
//            String emailParam = req.getParameter("email");
//            String roleParam = req.getParameter("role");
//            if (idParam != null){
//                //Get the user by id if present
//                int id = Integer.parseInt(idParam);
//                User user = userService.getUserById(id);
//                String userJSON = mapper.writeValueAsString(user);
//                writer.write(userJSON);
//                resp.setStatus(200);
//            }
//            if (emailParam != null){
//                //Get the user by id if present
//                User user = userService.getUserByEmail(emailParam);
//                String userJSON = mapper.writeValueAsString(user);
//                writer.write(userJSON);
//                resp.setStatus(200);
//            }
//            if (roleParam != null){
//                //Get all users by role if present
//                List<User> users = userService.getUsersByRole(roleParam);
//                String userJSON = mapper.writeValueAsString(users);
//                writer.write(userJSON);
//                resp.setStatus(200);
//            }

            Integer loggedInRole = (Integer) req.getSession().getAttribute("loggedinrole");
            //check to see if Admin role before sending all users
            if (loggedInRole == 1){
                List<User> users = userService.getAllUsers();
                String usersJSON = mapper.writeValueAsString(users);
                writer.write(usersJSON);
                resp.setStatus(200); //not required will be 200 by default
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


    /**
     * getting the do post method
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        try {
            //read BODY OF REQUEST
            //create new appuser from the json request
            User newUser = mapper.readValue(req.getInputStream(),User.class);
            userService.register(newUser);
            System.out.println(newUser);
            String newUserJSON = mapper.writeValueAsString(newUser);
            respWriter.write(newUserJSON);
            resp.setStatus(201); // 201 = created
        }catch (MismatchedInputException upe) {
            upe.printStackTrace();
            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(400,"Bad Request: malformed user object found in request body");
            respWriter.write(mapper.writeValueAsString(err));

        }catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500); //500 = internal server error
            ErrorResponse err = new ErrorResponse(500,"server: my bad.");
            respWriter.write(mapper.writeValueAsString(err));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();

        try {
            Integer loggedInRole = (Integer) req.getSession().getAttribute("loggedinrole");
            if (loggedInRole == 1){
                User updatedUser = mapper.readValue(req.getInputStream(),User.class);
                System.out.println(updatedUser.toString());
                userService.update(updatedUser);
                resp.setStatus(201); // 201 = created
            }
        } catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500); //500 = internal server error
            ErrorResponse err = new ErrorResponse(500,"server: my bad.");
            respWriter.write(mapper.writeValueAsString(err));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();

        try {
            Integer loggedInRole = (Integer) req.getSession().getAttribute("loggedinrole");
            if (loggedInRole == 1){
                int idParam = Integer.parseInt(req.getParameter("id"));
                System.out.println("id to delete: " + idParam);
                if (userService.deleteUserById(idParam)) {
                    resp.setStatus(201); // 201 = created
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            resp.setStatus(500); //500 = internal server error
            ErrorResponse err = new ErrorResponse(500,"server: my bad.");
            respWriter.write(mapper.writeValueAsString(err));
        }

    }
}
