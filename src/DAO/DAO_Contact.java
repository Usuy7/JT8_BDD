package DAO;

import ENTITY.contact;
import static METODOS.metodos.isLetra;
import static METODOS.metodos.isNum;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Javier
 */
public class DAO_Contact extends Conexion_DAO {

    static BufferedReader tc = new BufferedReader(new InputStreamReader(System.in));

    public void show(Connection con) throws Exception {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM contactos");

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

        } catch (SQLException e) {
            System.out.println("Error showing contacts: " + e.getMessage());
        } finally {
            if (!rs.isClosed() || !st.isClosed()) {
                rs.close();
                st.close();
            }
        }
    }

    public void add(Connection con) throws Exception {
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;

        try {

            System.out.println("\n*****New Contact*****");
            String aux;
            boolean val = false;
            int birthday, birth_month, year_birth;

            System.out.print("\nID: ");
            String id = tc.readLine();

            stmt = con.prepareStatement("SELECT Id FROM contactos");
            rs = stmt.executeQuery();

            ArrayList<String> num = new ArrayList();
            while (rs.next()) {
                String ID = rs.getString("Id");
                num.add(ID);
            }
            rs.close();
            stmt.close();

            while (!val) {
                for (int i = 0; i < num.size(); i++) {
                    if (num.get(i).equalsIgnoreCase(id) || isNum(id) == false) {
                        System.out.print("Error: ");
                        id = tc.readLine();
                    } else {
                        val = true;
                    }
                }
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
                System.out.print("Error, enter numbers: ");
                phone = tc.readLine();
            }

            do {
                System.out.print("Birthday: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.print("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                birthday = Integer.parseInt(aux);
                if (birthday < 1 || birthday > 31) {
                    System.out.print("That day is not valid: ");
                    birthday = Integer.parseInt(tc.readLine());
                } else {
                    val = true;
                }
            } while (!val);

            do {
                System.out.print("Birth month: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.print("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                birth_month = Integer.parseInt(aux);
                if (birth_month < 1 || birth_month > 12) {
                    System.out.println("That month is not valid: ");
                    birth_month = Integer.parseInt(tc.readLine());
                } else {
                    val = true;
                }
            } while (!val);

            do {
                System.out.print("Year of birth: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.print("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                year_birth = Integer.parseInt(aux);
                if (year_birth < 1900) {
                    System.out.println("That year is not valid: ");
                    year_birth = Integer.parseInt(tc.readLine());
                } else {
                    val = true;
                }
            } while (!val);

            birth_month--;
            year_birth -= 1900;

            System.out.println("\nContact addeded");

            Date birthdate = new Date(year_birth, birth_month, birthday);

            contact c = new contact(id, name, surname, street, phone, birthdate);

            stmt2 = con.prepareStatement("INSERT INTO contactos (Id, Name, Surname, Street, Phone, Birthdate) VALUES (?,?,?,?,?,?)");
            stmt2.setString(1, c.getId());
            stmt2.setString(2, c.getName());
            stmt2.setString(3, c.getSurname());
            stmt2.setString(4, c.getStreet());
            stmt2.setString(5, c.getPhone());
            stmt2.setDate(6, (c.getBirthdate()));
            stmt2.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding contact: " + e.getMessage());
        } finally {
            if (!stmt2.isClosed()) {
                stmt2.close();
            }
        }
    }

    public void update(Connection con) throws Exception {
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;
        try {

            System.out.println("\n*****Update Contacto*****");
            String aux;
            boolean val = false;
            int birthday, birth_month, year_birth;

            stmt = con.prepareStatement("SELECT Id FROM contactos");
            rs = stmt.executeQuery();

            ArrayList<String> num = new ArrayList();
            while (rs.next()) {
                String ID = rs.getString("Id");
                num.add(ID);
                System.out.print(ID + " ");
            }
            rs.close();
            stmt.close();

            System.out.print("\nEnter the contact ID you want to update: ");
            String id = tc.readLine();

            while (!val) {
                for (int i = 0; i < num.size(); i++) {
                    if (num.get(i).equalsIgnoreCase(id) || isNum(id) == false) {
                        val = true;
                    } else {
                        System.out.print("Error: ");
                        id = tc.readLine();
                    }
                }
            }

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
                    System.out.print("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                birthday = Integer.parseInt(aux);
                if (birthday < 1 || birthday > 31) {
                    System.out.print("That day is not valid: ");
                    birthday = Integer.parseInt(tc.readLine());
                } else {
                    val = true;
                }
            } while (!val);

            do {
                System.out.print("Birth month: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.print("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                birth_month = Integer.parseInt(aux);
                if (birth_month < 1 || birth_month > 12) {
                    System.out.println("That month is not valid: ");
                    birth_month = Integer.parseInt(tc.readLine());
                } else {
                    val = true;
                }
            } while (!val);

            do {
                System.out.print("Year of birth: ");
                aux = tc.readLine();

                while (isNum(aux) == false) {
                    System.out.print("Error, enter numbers: ");
                    aux = tc.readLine();
                }
                year_birth = Integer.parseInt(aux);
                if (year_birth < 1900) {
                    System.out.println("That year is not valid: ");
                    year_birth = Integer.parseInt(tc.readLine());
                } else {
                    val = true;
                }
            } while (!val);

            birth_month--;
            year_birth -= 1900;
            Date birthdate = new Date(year_birth, birth_month, birthday);

            System.out.println("\nContact udapted");

            stmt2 = con.prepareStatement("UPDATE contactos SET Name = ?, Surname = ?, Street = ?, Phone = ?, Birthdate = ? where Id =" + id);
            stmt2.setString(1, name);
            stmt2.setString(2, surname);
            stmt2.setString(3, street);
            stmt2.setString(4, phone);
            stmt2.setDate(5, birthdate);
            stmt2.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating contact: " + e.getMessage());
        } finally {
            if (!stmt2.isClosed()) {
                stmt2.close();
            }
        }
    }

    public void search(Connection con) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            System.out.println("\n*****Search Contact*****");

            System.out.print("\nEnter the contact ID you want to search: ");
            String Id = tc.readLine();

            stmt = con.prepareStatement("SELECT * FROM contactos WHERE Id =" + Id);
            rs = stmt.executeQuery();

            System.out.println("\nContact found: ");

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

        } catch (SQLException e) {
            System.out.println("Error searching contact: " + e.getMessage());
        } finally {
            if (!rs.isClosed() || !stmt.isClosed()) {
                rs.close();
                stmt.close();
            }
        }
    }

    public void delete(Connection con) throws Exception {
        PreparedStatement stmt = null;
        try {

            System.out.println("\n*****Delete Contact*****");

            System.out.print("\nEnter the contact ID you want to delete: ");
            String id = tc.readLine();

            System.out.println("\nContact deleted");

            stmt = con.prepareStatement("DELETE FROM contactos WHERE Id =" + id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting contact: " + e.getMessage());
        } finally {
            if (!stmt.isClosed()) {
                stmt.close();
            }
        }
    }

    public void sort(Connection con) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {

            System.out.println("\n*****Sort Agenda*****");

            System.out.print("\nEnter the field for which you want to sort the agenda: ");
            String aux = tc.readLine();

            stmt = con.prepareStatement("SELECT * FROM contactos ORDER BY " + aux);
            rs = stmt.executeQuery();

            System.out.println("\nOrdered agenda: ");

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
        } catch (SQLException e) {
            System.out.println("Error sorting contact: " + e.getMessage());
        } finally {
            if (!rs.isClosed() || !stmt.isClosed()) {
                rs.close();
                stmt.close();
            }
        }
    }

    public ArrayList<contact> agenda_savetoFile(Connection con) throws SQLException {

        ArrayList<contact> agenda = new ArrayList<>();
        
        PreparedStatement stmt = null;
        ResultSet rs = null;   
        
        try {
            stmt = con.prepareStatement("SELECT * FROM contactos");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("Id");
                String name = rs.getString("Name");
                String surname = rs.getString("Surname");
                String street = rs.getString("Street");
                String phone = rs.getString("Phone");
                Date birthdate = rs.getDate("Birthdate");
                contact c = new contact(id, name, surname, street, phone, birthdate);
                agenda.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error showing contacts: " + e.getMessage());
        }  finally {
            if (!rs.isClosed() || !stmt.isClosed()) {
                rs.close();
                stmt.close();
            }
        }
        return agenda;
    }

    public void agenda_updateDatabase(Connection con, ArrayList<contact> agenda) throws Exception {
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;

        try {
            stmt = con.prepareStatement("DELETE * FROM contactos");
            stmt.executeUpdate();

            for (contact c : agenda) {
                stmt2 = con.prepareStatement("INSERT INTO contactos (Id, Name, Surname, Street, Phone, Birthdate) VALUES (?,?,?,?,?,?)");
                stmt2.setString(1, c.getId());
                stmt2.setString(2, c.getName());
                stmt2.setString(3, c.getSurname());
                stmt2.setString(4, c.getStreet());
                stmt2.setString(5, c.getPhone());
                stmt2.setDate(6, (c.getBirthdate()));
                stmt2.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Error deleting contact: " + e.getMessage());
        } finally {
            if (!stmt.isClosed()) {
                stmt.close();
            }
        }
    }
}
