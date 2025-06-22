package Actions;

import Main.EmptySelectionException;
import Main.SQLManager;
import Main.Selector;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Apmokejimas {
    private static Scanner _scan = new Scanner(System.in);

    public static void Apmoketi(SQLManager manager) throws EmptySelectionException
    {
        System.out.println("Pasirinkite apmokejimo buda:\n likutis");
        String objektas = _scan.nextLine();

        try
        {
            if (objektas.toLowerCase().equals("likutis")){
                String VairuotojoAK = Selector.selectVairuotojoAK(manager, "Pasirinkite vairuotoja, kurio rezervacijos bus apmoketos is likucio pinigu");

                System.out.println("Apmokamos praejusios vairuotojo rezervacijos...");
                List<BigDecimal> likutisIrSuma = manager.getLikutisIrSuma(VairuotojoAK);
                if(likutisIrSuma.size() == 0) {
                    System.out.println("Nera neapmoketu rezervaciju");
                }
                else {
                    if (likutisIrSuma.get(0).compareTo(likutisIrSuma.get(1)) < 0) {
                        System.out.println("Nepakanka pinigu saskaitoje apmoketi visoms rezervacijoms");
                    } else {
                        manager.apmoketiIsLikucio(VairuotojoAK);
                        System.out.println("Rezervacijos apmoketos");
                    }
                }
            }
            else{
                System.out.println("Neatpazintas ieskomas objektas " + objektas);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Netiketa klaida bandant rasti objekta");
            System.out.println("Klaidos pranesimas: " + e.getMessage());
        }
    }
}
