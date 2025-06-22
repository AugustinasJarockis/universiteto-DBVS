package Actions;

import Main.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Trynimas {
    private static Scanner _scan = new Scanner(System.in);

    public static void Trinti(SQLManager manager) throws EmptySelectionException {
        System.out.println("Pasirinkite ką norite ištrinti:\n rezervacija");
        String objektas = _scan.nextLine();

        try
        {
            if (objektas.toLowerCase().equals("rezervacija")){
                  int rezervacijosNr = Selector.selectRezervacijosNr(manager, "Pasirinkite norimos pašalinti rezervacijos numerį:");
                  System.out.println("Salinama rezervacija...");
                  manager.deleteRezervacija(rezervacijosNr);
                  System.out.println("Rezervacija pasalinta");
            }
            else{
                System.out.println("Neatpazintas registruojamas objektas " + objektas);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Netiketa klaida bandant uzregistruoti objekta");
            System.out.println("Klaidos pranesimas: " + e.getMessage());
        }
    }
}
