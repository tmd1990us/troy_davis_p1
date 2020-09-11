package com.revature.controller.admin;

import com.revature.repositories.ReimbursementsRepository;
import com.revature.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;

public class AdminDashboardController {
    public static String adminDash(HttpServletRequest req, ReimbursementsRepository reimbRepo, UserRepository userRepository) {
        return "/html/admin/admin_dashboard.html";
    }
}
