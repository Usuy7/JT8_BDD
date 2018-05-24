package MAIN;

import DAO.Conexion_DAO;
import DAO.DAO_Contact;
import ENTITY.contact;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *
 * @author Javier
 */
public class agenda extends DAO_Contact{

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    Conexion_DAO con = new Conexion_DAO();
    DAO_Contact dao = new DAO_Contact();
    contact c = new contact();
    
    public static void main(String[] args) throws IOException, Exception {
        new agenda();
    }

    static BufferedReader tc = new BufferedReader(new InputStreamReader(System.in));

    public agenda() throws IOException, Exception {
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
                    + "7.Exit\n"
                    + "Choose an option: ");
            opc = Integer.parseInt(tc.readLine());
            validate(opc);
            menu(opc);
        } while (opc != 7);
    }

    public void validate(int opc) throws IOException, Exception {
        while (opc < 1 || opc > 7) {
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
                dao.update(con.conexion, c);
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
            case 7: // EXIT
                dao.disconnect_BDD();
                System.out.println("Bye bye");
                break;
        }
    }
}
