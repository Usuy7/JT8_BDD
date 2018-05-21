package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    Connection con;

    public Connection AbrirConexion() throws ClassNotFoundException {

        try {
            Class.forName("com.mysql.jdbc.Driver"); //DRIVER

            String url = "jdbc:mysql://localhost:3306/agenda";
            String user = "root";
            String pass = "";
            con = DriverManager.getConnection(url, user, pass);           

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR EN LA CONEXION CON BBDD");
        }

        return con;
    }

    public void CerrarConexion() {

        if (con != null) {
            try {
                con.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
