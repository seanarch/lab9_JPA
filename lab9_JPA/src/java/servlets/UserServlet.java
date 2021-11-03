/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dataaccess.RoleDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.RoleService;
import services.UserService;

/**
 *
 * @author Sean
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserService us = new UserService();
        RoleService rs = new RoleService();
        RoleDB roleDB = new RoleDB();
        String action = request.getParameter("action");
        String userPrimaryKey = "";

        if (action == null) {//show all users
            try {
                List<User> userList = us.getAll();
                session.setAttribute("roles", roleDB);
                session.setAttribute("userList", userList);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equals("edit")) {

            userPrimaryKey = request.getParameter("userPrimaryKey");
            User selectedUser = null;
            
            try {
                selectedUser = us.get(userPrimaryKey);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("selectedUser", selectedUser);
        } 

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserService us = new UserService();
        RoleService rs = new RoleService();

        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    String email = request.getParameter("email");
                    String active_string = request.getParameter("active");
                    boolean active = true;
                    if (active_string == null) {
                        active = false;
                    }
                    String firstName = request.getParameter("firstName");
                    String lastName = request.getParameter("lastName");
                    String password = request.getParameter("password");
                    int role = Integer.parseInt(request.getParameter("role"));
                    User user = new User(email, active, firstName, lastName, password, role);

                    if (AllFieldsFilled(email, active, firstName, lastName, password, role)) {
                        us.insert(email, active, firstName, lastName, password, role);
                    } else {
                        request.setAttribute("user", user);
                        request.setAttribute("errorMessage_EddUser", true);
                        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                        return;
                    }
                    break;
                case "edit":
                    String Update_email = request.getParameter("update_email");
                    String Update_firstName = request.getParameter("update_firstName");
                    String Update_lastName = request.getParameter("update_lastName");
                    String Update_password = request.getParameter("update_password");
                    int Update_role = Integer.parseInt(request.getParameter("update_role"));
                    String Update_active_string = request.getParameter("update_active");
                    boolean Update_active = true;
                    if (Update_active_string == null) {
                        Update_active = false;
                    }
                    User Update_user = new User(Update_email, Update_active, Update_firstName, Update_lastName, Update_password, Update_role);

                    if (AllFieldsFilled(Update_email, Update_active, Update_firstName, Update_lastName, Update_password, Update_role)) {
                        us.update(Update_email, Update_active, Update_firstName, Update_lastName, Update_password, Update_role);
                    } else {
                        request.setAttribute("selectedUser", Update_user);
                        request.setAttribute("errorMessage_EditUser", true);
                    }
                    break;
                case "delete": {

                    String userPrimaryKey = request.getParameter("userPrimaryKey");
                    try {
                        us.delete(userPrimaryKey);
                   
                        List<User> userList = us.getAll();
                        RoleDB roleDB = new RoleDB();
                        session.setAttribute("roles", roleDB);
                        session.setAttribute("userList", userList);

                    } catch (Exception ex) {
                        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "cancel":
                    break;

                default:
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            List<User> userList = us.getAll();
            session.setAttribute("userList", userList);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }

    private boolean AllFieldsFilled(String email, boolean active, String firstName, String lastName, String password, int role) {
        if (email == null || email.equals("") || firstName == null || firstName.equals("")
                || lastName == null || lastName.equals("")
                || password == null || password.equals("")) {
            return false;
        }
        return true;
    }
}
