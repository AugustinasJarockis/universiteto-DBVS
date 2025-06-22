package Actions;

import Main.EmptySelectionException;
import Main.SQLManager;
import Main.Selector;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Keitimas {
    private static Scanner _scan = new Scanner(System.in);

    public static void Keisti(SQLManager manager) throws EmptySelectionException
    {
        System.out.println("Pasirinkite ką norite pakeisti:\n likuti");
        String objektas = _scan.nextLine();

        try
        {
            if (objektas.toLowerCase().equals("likuti")){
                String VairuotojoAK = Selector.selectVairuotojoAK(manager, "Įveskite vairuotojo, kurio likutį norite pakeisti, numerį:");
                String dabartinisLikutis = (String)manager.getConcreteVairuotojas(VairuotojoAK).get(0).get(5);

                while(true)
                {
                    System.out.println("Dabartinis likutis: " + dabartinisLikutis);
                    System.out.println("Įveskite naują likutį:");
                    try
                    {
                        BigDecimal naujasLikutis = _scan.nextBigDecimal();
                        System.out.println("Keičiamas vairuotojo likutis...");
                        manager.updateLikutis(VairuotojoAK, naujasLikutis);
                        System.out.println("Vairutotojo likutis pakeistas");
                        break;
                    }
                    catch (InputMismatchException ime)
                    {
                        _scan.nextLine();
                        System.out.println("Įveskite SKAIČIŲ");
                    }
                }

            }
            else{
                System.out.println("Neatpazintas ieskomas objektas " + objektas);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Netikėta klaida bandant rasti objektą");
            System.out.println("Klaidos pranešimas: " + e.getMessage());
        }
    }
}
