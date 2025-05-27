package org.martin.compra.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    //Inicializo 3 variables globales
    private static String url = "jdbc:mysql://localhost:3306/trabajoenclase?useTimezone=UTC";
    //nombre del usuario de la base de datos
    private static String username = "root";
    //contraseña de la base de datos
    private static String password = "";

    //implelmentamos un metodo para realizar la conexion
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    //main
    public static void main(String[] args) {
        System.out.println("Intentando conectar a la base de datos...");

        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("¡Conexión exitosa!");
                System.out.println("Base de datos: " + connection.getCatalog());
                System.out.println("URL: " + url);
            } else {
                System.out.println("Conexión rechazada: La conexión es nula");
            }
        } catch (SQLException e) {
            System.out.println("Conexión rechazada: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
