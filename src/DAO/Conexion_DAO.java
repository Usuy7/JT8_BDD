package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class Conexion_DAO {

    public Connection conexion;

    // JDBC driver nombre y base de datos URL
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/agenda";

    // Credenciales de la base de datos
    private final String USER = "root";
    private final String PASS = "";

    // Abrir conexion a la base de datos
    public void connect_BDD() {
        try {
            Class.forName(JDBC_DRIVER);
            conexion = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error connecting to the BDD: " + e.getMessage());
        }
    }

    // Cerrar conexion a la base de datos
    public void disconnect_BDD() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Conexion_DAO.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
        }
    }
}