package com.revature.models.controller;

import javax.servlet.http.HttpServletRequest;

public class SubmitReimbursementController {
    public static String home (HttpServletRequest req){
        //a tun of business logic can go here
        return  "/html/employee/submit_reimbursement_request.html";
    }
}
