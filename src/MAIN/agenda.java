package MAIN;

import DAO.Conexion_DAO;
import DAO.DAO_Contact;
import ENTITY.contact;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class agenda extends DAO_Contact {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, Exception {
        new agenda();
    }

    Conexion_DAO con = new Conexion_DAO();
    DAO_Contact dao = new DAO_Contact();
    static BufferedReader tc = new BufferedReader(new InputStreamReader(System.in));
    static ArrayList<contact> agenda = new ArrayList<contact>();
    static File fichero = new File("Agenda.txt");

    public agenda() throws IOException, Exception {
        recover_caracter();
        con.connect_BDD();
        start();
    }

    public void start() throws IOException, Exception {
        int opc;
        do {
            System.out.print("\n***AGENDA MANAGER***\n"
                    + "1.Show\n"
                    + "2.Add\n"
                    + "3.Update\n"
                    + "4.Search\n"
                    + "5.Delete\n"
                    + "6.Sort\n"
                    + "7.Update Database with File's data\n"
                    + "8.Exit\n"
                    + "Choose an option: ");
            opc = Integer.parseInt(tc.readLine());
            validate(opc);
            menu(opc);
        } while (opc != 8);
    }

    public void validate(int opc) throws IOException, Exception {
        while (opc < 1 || opc > 8) {
            System.out.print("Option not valid, enter again: ");
            opc = Integer.parseInt(tc.readLine());
            validate(opc);
            menu(opc);
        }
    }

    public void menu(int opc) throws Exception {
        switch (opc) {
            case 1: // SHOW
                dao.show(con.conexion);
                break;
            case 2: // ADD
                dao.add(con.conexion);
                break;
            case 3: // UPDATE
                dao.update(con.conexion);
                break;
            case 4: // SEARCH
                dao.search(con.conexion);
                break;
            case 5: // DELETE
                dao.delete(con.conexion);
                break;
            case 6: //SORT
                dao.sort(con.conexion);
                break;
            case 7: // UPDATE DATABASE WITH FILE'S DATA
                dao.agenda_updateDatabase(con.conexion, agenda);
                break;
            case 8: // EXIT
                //overwrite(dao.agenda_savetoFile(con.conexion));
                dao.disconnect_BDD();
                System.out.println("\nBye bye");
                break;
        }
    }

    public void overwrite(ArrayList<contact> agenda) {
        try {
            System.out.println("\nSaving the data in the file ...");
            ObjectOutputStream serializar = new ObjectOutputStream(new FileOutputStream(fichero, false));
            serializar.writeObject(agenda);
            serializar.close();
            System.out.println("All data has been properly saved");
        } catch (FileNotFoundException e) { // qué hacer si no se encuentra el fichero
            System.out.println("No se encuentra el fichero");
        } catch (IOException e) { // qué hacer si hay un error en la lectura del fichero
            System.out.println("No se puede leer el fichero ");
        }
    }

    public void recover_bite() {
        try {
            if (fichero.exists()) {
                // RECUPERAMOS el fichero del formato byte.
                System.out.println("Accessing file...");
                ObjectInputStream recuperar = new ObjectInputStream(new FileInputStream(fichero));
                agenda = (ArrayList<contact>) recuperar.readObject();
                recuperar.close();

            } else {
                // Si NO EXISTE, CREAMOS el fichero
                System.out.println("File created...");
                FileWriter escritura = new FileWriter(fichero, true);
                BufferedWriter buffer = new BufferedWriter(escritura);
                buffer.close();
            }
        } catch (FileNotFoundException e) { // qué hacer si no se encuentra el fichero
            System.out.println("No se encuentra el fichero");
        } catch (ClassNotFoundException e) { // qué hacer si no se encuentra el fichero
            System.out.println("No se encuentra una clase con ese nombre de definición");
        } catch (IOException e) { // qué hacer si hay un error en la lectura del fichero
            System.out.println("No se puede leer el fichero ");
        }
    }

    public void recover_caracter() {
        try {
            if (fichero.exists()) {
                // RECUPERAMOS el fichero del formato byte.
                System.out.println("Accessing file...");
                FileReader entrada = new FileReader(fichero);
                BufferedReader buffer = new BufferedReader(entrada);

                String linea = buffer.readLine();
            
            while (linea != null){
                StringTokenizer token = new StringTokenizer(linea,"	");
                String id = token.nextToken();
                String name = token.nextToken();
                String surname = token.nextToken();
                String street = token.nextToken();
                String phone = token.nextToken();
                String fecha = token.nextToken();
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthdate = (Date) sdf.parse(fecha);
                
                agenda.add(new contact(id, name, surname, street, phone, birthdate));
                linea = buffer.readLine();
            }
            for (int i = 0; i < agenda.size(); i++) {
                System.out.println(agenda.get(i).toString());
            }
            buffer.close();
                
            } else {
                // Si NO EXISTE, CREAMOS el fichero
                System.out.println("File created...");
                FileWriter escritura = new FileWriter(fichero, true);
                BufferedWriter buffer = new BufferedWriter(escritura);
                buffer.close();
            }
        } catch (FileNotFoundException e) { // qué hacer si no se encuentra el fichero
            System.out.println("No se encuentra el fichero");
        } // qué hacer si no se encuentra el fichero
        catch (IOException e) { // qué hacer si hay un error en la lectura del fichero
            System.out.println("No se puede leer el fichero ");
        } catch (ParseException ex) {
            Logger.getLogger(agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
