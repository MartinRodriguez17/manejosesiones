package org.martin.compra.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.martin.compra.services.LoginService;
import org.martin.compra.services.LoginServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

// Definición del Servlet que manejará las rutas /login y /login.html
@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {
    // Credenciales fijas para autenticación (en producción usar base de datos)
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    // Método para manejar solicitudes GET (cuando se accede a la página)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener todas las cookies del request, si no hay crear arreglo vacío
       /* Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[0];

        // Buscar la cookie llamada "username" usando Streams de Java
        Optional<String> cookieOptional = Arrays.stream(cookies)
                .filter(c -> "username".equals(c.getName()))  // Filtrar por nombre de cookie
                .map(Cookie::getValue)  // Obtener solo el valor de la cookie
                .findAny();  // Tomar la primera coincidencia*/

       //Implementamos el objetivo de tipo sesion
        LoginService auth= new LoginServiceImplement();
        //Creamos una variable opcional
        //Para obtener el nombre del usuario
        Optional<String>usernameOptional = auth.getUserName(req);

        // Si existe la cookie (usuario ya autenticado)
        if (usernameOptional.isPresent()) {
            // Configurar tipo de contenido de la respuesta
            resp.setContentType("text/html;charset=UTF-8");

            // Usar try-with-resources para manejo automático del PrintWriter
            try (PrintWriter out = resp.getWriter()) {
                // Generar HTML de respuesta dinámicamente
                out.print("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");  // Especificar encoding
                out.println("<title>Hola"+ usernameOptional+"</title>");  // Título de la pestaña
                out.println("</head>");
                out.println("<body>");
                // Mostrar mensaje personalizado con el nombre de usuario
                out.println("<h1>Hola "+ usernameOptional.get()+" ya iniciaste sesión anteriormente!</h1>");
                // Enlace para volver al inicio
                out.println("<p><a href='index.html'>Volver al inicio</a></p>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            // Si no hay cookie, mostrar el formulario de login (JSP)
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    // Método para manejar solicitudes POST (envío del formulario de login)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Validar credenciales
        if (username.equals(USERNAME) && password.equals(PASSWORD)) {

            //Creamos la sesión
            HttpSession session = req.getSession();
            //Seteo los valores de la sesion
            session.setAttribute("username", username);

            // Redirigir a la página de login (mostrará mensaje de bienvenida)
            resp.sendRedirect("login.html");
        } else {
            // Si credenciales son inválidas, enviar error HTTP 401 (No autorizado)
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos no tiene acceso");
        }
    }
}