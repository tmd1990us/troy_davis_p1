package com.revature.util;



import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
    public String process(HttpServletRequest req) {
        Integer loggedInRole = (Integer) req.getSession().getAttribute("loggedinrole");
        String pr = (String) req.getSession().getAttribute("principal");
        switch (req.getRequestURI()) {
            case "/ers/login.view":
            case "/login.view":
                return "partials/login.html";

            case "/ers/success.view":
            case "/success.view":
                return "partials/animations/success.html";

            case "/ers/failure.view":
            case "/failure.view":
                return "partials/animations/failure.html";

            case "/ers/landing.view":
            case "/landing.view":
                return "partials/landing.html";

            case "/ers/home_nav.view":
            case "/home_nav.view":
                return "partials/home_nav.html";

            case "/ers/register.view":
            case "/register.view":
                return "partials/create_account.html";

            case "/ers/admin_add_user.view":
            case "/admin_add_user.view":
                if (loggedInRole == 1){
                    return "partials/admin/add_user.html";
                }

            case "/ers/view_all_users.view":
            case "/view_all_users.view":
                if (loggedInRole == 1) {
                    return "partials/admin/view_users.html";
                }

            case "/ers/update_user.view":
            case "/update_user.view":
                if (loggedInRole == 1) {
                    return "partials/admin/update_user.html";
                }

            case "/ers/delete_user.view":
            case "/delete_user.view":
                if (loggedInRole == 1) {
                    return "partials/admin/delete_user.html";
                }

            case "/ers/submit_reimb.view":
            case "/submit_reimb.view":
                if (loggedInRole == 2 || loggedInRole == 3) {
                    return "partials/emp/submit_reimb.html";
                }

            case "/ers/view_emp_reimb.view":
            case "/view_emp_reimb.view":
                if (loggedInRole == 3) {
                    return "partials/emp/view_reimb.html";
                }

            case "/ers/view_all_reimb.view":
            case "/view_all_reimb.view":
                if (loggedInRole == 2) {
                    return "partials/fin/view_reimb.html";
                }

            case "/ers/update_emp_reimb.view":
            case "/update_emp_reimb.view":
                if (loggedInRole == 3) {
                    return "partials/emp/update_reimb.html";
                }

            case "/ers/update_fin_reimb.view":
            case "/update_fin_reimb.view":
                if (loggedInRole == 2) {
                    return "partials/fin/reimb_detail.html";
                }

            case "/ers/home.view":
            case "/home.view":
                if(pr == null || pr.equals("")){
                    //user not logged in
                    return "partials/login.html";
                }

                System.out.println("Logged in role is"+loggedInRole);
                if (loggedInRole == 3){
                    System.out.println("sending to EmployeeDash");
                    return "/partials/emp/dash.html";
                } else if (loggedInRole == 1){
                    System.out.println("sending to AdminDash");
                    return "/partials/admin/dash.html";
                } else if (loggedInRole == 2){
                    System.out.println("sending to FinManDash");
                    return "/partials/fin/dash.html";
                }
            default:
                return "/partials/animations/unauth.html";

        }
    }
}
