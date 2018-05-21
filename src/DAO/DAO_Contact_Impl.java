package DAO;

import ENTITY.contact;
import static METODOS.metodos.isLetra;
import static METODOS.metodos.isNum;
import interfaces.DAO_Contact;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Javier
 */
public class DAO_Contact_Impl extends connection implements DAO_Contact {

    static BufferedReader tc = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void show() throws Exception {
        try {
            this.connect_BDD(); // ABRIMOS LA CONEXIÓN A LA BDD
            Statement st = this.conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contactos");

            System.out.println("***CONTACTS***");

            while (rs.next()) {
                String id = rs.getString("Id");
                String name = rs.getString("Name");
                String surname = rs.getString("Surname");
                String street = rs.getString("Street");
                String phone = rs.getString("Phone");
                Date birthdate = rs.getDate("Birthdate");

                System.out.println("\nID: " + id + "\nName: " + name + "\nSurname: " + surname + "\nStreet: " + street + "\nPhone: " + phone + "\nBirthdate: " + birthdate);
                System.out.println("**********");
            }
            rs.close();
            
        } catch (SQLException e) {
            System.out.println("Error showing contacts: " + e.getMessage());
        } finally {
            this.disconnect_BDD(); // CERRAMOS LA CONEXIÓN A LA BDD
        }
    }

    @Override
    public void add() throws Exception {
        try {

            System.out.println("\n***New Contacto***");

            System.out.print("\nID: ");
            String id = tc.readLine();
            while (isNum(id) == false) {
                System.out.println("Error, enter numbers: ");
                id = tc.readLine();
            }

            System.out.print("Name: ");
            String name = tc.readLine();
            isLetra(name);

            System.out.print("Surname: ");
            String surname = tc.readLine();
            isLetra(surname);

            System.out.print("Street: ");
            String street = tc.readLine();

            System.out.print("Phone: ");
            String phone = tc.readLine();
            while (isNum(phone) == false) {
                System.out.println("Error, enter numbers: ");
                phone = tc.readLine();
            }

            System.out.print("Birthdate format dd/MM/yyyy: ");
            String birthdate = tc.readLine();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = sdf.parse(birthdate);

            while (!sdf.format(fecha).equals(birthdate)) {
                System.out.println("Invalid format: ");
                birthdate = tc.readLine();
                fecha = sdf.parse(birthdate);
            }

            contact c = new contact(id, name, surname, street, phone, fecha);

            this.connect_BDD(); // ABRIMOS LA CONEXIÓN A LA BDD
            PreparedStatement stmt = this.conexion.prepareStatement("INSERT INTO contactos (Id, Name, Surname, Street, Phone, Birthdate) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, c.getId());
            stmt.setString(2, c.getName());
            stmt.setString(3, c.getSurname());
            stmt.setString(4, c.getStreet());
            stmt.setString(5, c.getPhone());
            stmt.setDate(6, (java.sql.Date) c.getBirthdate());
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Error adding contact: " + e.getMessage());
        } finally {
            this.disconnect_BDD(); // CERRAMOS LA CONEXIÓN A LA BDD
        }
    }

    @Override
    public void update() throws Exception {
        try {

            System.out.print("\nEnter the contact ID you want to update: ");
            String id = tc.readLine();

            System.out.print("New Name: ");
            String name = tc.readLine();
            isLetra(name);

            System.out.print("New Surname: ");
            String surname = tc.readLine();
            isLetra(surname);

            System.out.print("New Street: ");
            String street = tc.readLine();

            System.out.print("New Phone: ");
            String phone = tc.readLine();
            while (isNum(phone) == false) {
                System.out.println("Error, enter numbers: ");
                phone = tc.readLine();
            }

            System.out.print("New Birthdate format dd/MM/yyyy: ");
            String birthdate = tc.readLine();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = sdf.parse(birthdate);

            while (!sdf.format(fecha).equals(birthdate)) {
                System.out.println("Invalid format: ");
                birthdate = tc.readLine();
                fecha = sdf.parse(birthdate);
            }    
            
            this.connect_BDD(); // ABRIMOS LA CONEXIÓN A LA BDD
            PreparedStatement stmt = this.conexion.prepareStatement("UPDATE contactos SET Name = ?, Surname = ?, Street = ?, Phone = ?, Birthdate = ? where Id =" + id);

            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, street);
            stmt.setString(4, phone);
            stmt.setDate(5, (java.sql.Date) fecha);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Error updating contact: " + e.getMessage());
        } finally {
            this.disconnect_BDD(); // CERRAMOS LA CONEXIÓN A LA BDD
        }
    }

    @Override
    public void search() throws Exception {
        try {
            
            System.out.print("\nEnter the contact ID you want to search: ");
            String Id = tc.readLine();
            
            this.connect_BDD(); // ABRIMOS LA CONEXIÓN A LA BDD
            PreparedStatement stmt = this.conexion.prepareStatement("SELECT * FROM contactos WHERE Id =" + Id);
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("***CONTACT***");
            
            while (rs.next()) {
                String id = rs.getString("Id");
                String name = rs.getString("Name");
                String surname = rs.getString("Surname");
                String street = rs.getString("Street");
                String phone = rs.getString("Phone");
                Date birthdate = rs.getDate("Birthdate");

                System.out.println("\nID: " + id + "\nName" + name + "\nSurname" + surname + "\nStreet" + street + "\nPhone" + phone + "\nBirthdate" + birthdate);
                System.out.println("**********");
            }
            rs.close();
            
        } catch (SQLException e) {
            System.out.println("Error searching contact: " + e.getMessage());
        } finally {
            this.disconnect_BDD(); // CERRAMOS LA CONEXIÓN A LA BDD
        }
    }

    @Override
    public void delete() throws Exception {
        try {
            
            System.out.print("\nEnter the contact ID you want to delete: ");
            String id = tc.readLine();
            
            this.connect_BDD(); // ABRIMOS LA CONEXIÓN A LA BDD
            PreparedStatement stmt = this.conexion.prepareStatement("DELETE FROM contactos WHERE Id =" + id);
            stmt.executeUpdate();
            stmt.close();
            
        } catch (SQLException e) {
            System.out.println("Error deleting contact: " + e.getMessage());
        } finally {
            this.disconnect_BDD(); // CERRAMOS LA CONEXIÓN A LA BDD
        }
    }

    @Override
    public void sort() throws Exception {
        try {
            this.connect_BDD(); // ABRIMOS LA CONEXIÓN A LA BDD
            PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM contactos  ");

        } catch (SQLException e) {
            System.out.println("Error sorting contact: " + e.getMessage());
        } finally {
            this.disconnect_BDD(); // CERRAMOS LA CONEXIÓN A LA BDD
        }
    }

}
