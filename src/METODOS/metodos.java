package METODOS;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Javier
 */
public class metodos {

    static BufferedReader tc = new BufferedReader(new InputStreamReader(System.in));
    
    public static boolean isNum(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String isLetra(String cadena) throws IOException {

        boolean val = false;

        do {

            while (cadena.equals("")) {
                System.out.print("Ingresa algún dato: ");
                cadena = tc.readLine();
            }

            for (int i = 0; i < cadena.length(); i++) {

                val = true;

                if (!Character.isLetter(cadena.charAt(i))) {
                    val = false;
                }
            }
            if (!val) {
                System.out.print("Error, ingresa solo carácteres alfabeticos: ");
                cadena = tc.readLine();
            }
        } while (!val);

        return cadena;
    }
}
