package DAO;

import ENTITY.contact;
import static METODOS.metodos.isLetra;
import static METODOS.metodos.isNum;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 *
 * @author Javier
 */
public class DAO_Contact extends Conexion_DAO {

    static BufferedReader tc = new BufferedReader(new InputStreamReader(System.in));

    public void show(Connection con) throws Exception {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM contactos");

            System.out.println("\n*****CONTACTS*****");

            while (rs.next()) {
                String id = rs.getString("Id");
                String name = rs.getString("Name");
                String surname = rs.getString("Surname");
                String street = rs.getString("Street");
                String phone = rs.getString("Phone");
                Date birthdate = rs.getDate("Birthdate");

                contact c = new contact(id, name, surname, street, phone, birthdate);

                System.out.println(c.toString());
                System.out.println("*********************");
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println("Error showing contacts: " + e.getMessage());
        }
    }

    public void add(Connection con) throws Exception {
        try {

            System.out.println("\n***New Contacto***");
            String aux;
            boolean val = false;
            int birthday, birth_month, year_birth;

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

            do {
                System.out.print("Birthday: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.println("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                birthday = Integer.parseInt(aux);
                while (birthday < 1 || birthday > 31) {
                    System.out.println("That day is not valid: ");
                    birthday = Integer.parseInt(aux);
                }
            } while (!val);

            do {
                System.out.println("Birth month: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.println("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                birth_month = Integer.parseInt(aux) + 1;
                while (birth_month < 1 || birth_month > 12) {
                    System.out.println("That month is not valid: ");
                    birth_month = Integer.parseInt(aux);
                }
            } while (!val);

            do {
                System.out.print("Year of birth: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.println("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                year_birth = Integer.parseInt(aux) - 1900;
                while (year_birth < 1900) {
                    System.out.println("That year is not valid: ");
                    year_birth = Integer.parseInt(aux);
                }
            } while (!val);

            Date birthdate = new Date(year_birth, birth_month, birthday);

            contact c = new contact(id, name, surname, street, phone, birthdate);

            PreparedStatement stmt = con.prepareStatement("INSERT INTO contactos (Id, Name, Surname, Street, Phone, Birthdate) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, c.getId());
            stmt.setString(2, c.getName());
            stmt.setString(3, c.getSurname());
            stmt.setString(4, c.getStreet());
            stmt.setString(5, c.getPhone());
            stmt.setDate(6, (c.getBirthdate()));
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Error adding contact: " + e.getMessage());
        }
    }

    public void update(Connection con) throws Exception {
        try {

            System.out.println("\n***Update Contacto***");
            String aux;
            boolean val = false;
            int birthday, birth_month, year_birth;
            
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

            do {
                System.out.print("Birthday: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.println("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                birthday = Integer.parseInt(aux);
                while (birthday < 1 || birthday > 31) {
                    System.out.println("That day is not valid: ");
                    birthday = Integer.parseInt(aux);
                }
            } while (!val);

            do {
                System.out.println("Birth month: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.println("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                birth_month = Integer.parseInt(aux) + 1;
                while (birth_month < 1 || birth_month > 12) {
                    System.out.println("That month is not valid: ");
                    birth_month = Integer.parseInt(aux);
                }
            } while (!val);

            do {
                System.out.print("Year of birth: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.println("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                year_birth = Integer.parseInt(aux) - 1900;
                while (year_birth < 1900) {
                    System.out.println("That year is not valid: ");
                    year_birth = Integer.parseInt(aux);
                }
            } while (!val);

            Date birthdate = new Date(year_birth, birth_month, birthday);

            PreparedStatement stmt = con.prepareStatement("UPDATE contactos SET Name = ?, Surname = ?, Street = ?, Phone = ?, Birthdate = ? where Id =" + id);
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, street);
            stmt.setString(4, phone);
            stmt.setDate(5, birthdate);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Error updating contact: " + e.getMessage());
        }
    }

    public void search(Connection con) throws Exception {
        try {

            System.out.print("\nEnter the contact ID you want to search: ");
            String Id = tc.readLine();

            PreparedStatement stmt = con.prepareStatement("SELECT * FROM contactos WHERE Id =" + Id);
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
        }
    }

    public void delete(Connection con) throws Exception {
        try {

            System.out.print("\nEnter the contact ID you want to delete: ");
            String id = tc.readLine();

            PreparedStatement stmt = con.prepareStatement("DELETE FROM contactos WHERE Id =" + id);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Error deleting contact: " + e.getMessage());
        }
    }

    public void sort(Connection con) throws Exception {
        try {
            PreparedStatement st = con.prepareStatement("SELECT * FROM contactos  ");

        } catch (SQLException e) {
            System.out.println("Error sorting contact: " + e.getMessage());
        }
    }

}
