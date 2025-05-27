package org.martin.compra.filtro;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.martin.compra.services.ServiceJdbcException;
import org.martin.compra.util.Conexion;

@WebFilter("/*")
public class ConexionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try(Connection conn = Conexion.getConnection()){
            if(conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            try{
                request.setAttribute("conn", conn);
                chain.doFilter(request, response);
                conn.commit();
            } catch(SQLException | ServiceJdbcException e){
                conn.rollback();
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

    }
}

