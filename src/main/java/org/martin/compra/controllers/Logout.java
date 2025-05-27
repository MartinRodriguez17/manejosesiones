package org.martin.compra.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.martin.compra.services.LoginService;
import org.martin.compra.services.LoginServiceImplement;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/logout")
public class Logout  extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Creamos el objeto de tipo session
        LoginService auth = new LoginServiceImplement();
        Optional<String> userNameOptional = auth.getUserName(req);
        if (userNameOptional.isPresent()) {
            HttpSession session = req.getSession();
            //Cerramos la sesion
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath()+"/login");
    }
}
