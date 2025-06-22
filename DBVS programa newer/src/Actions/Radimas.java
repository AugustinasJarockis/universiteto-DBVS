package Actions;

import Main.EmptySelectionException;
import Main.InputValidator;
import Main.SQLManager;
import Main.Selector;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Radimas {
    private static Scanner _scan = new Scanner(System.in);

    public static void Rasti(SQLManager manager) throws EmptySelectionException
    {
        System.out.println("Pasirinkite ka norite rasti:\n automobili  finansus");
        String objektas = _scan.nextLine();

        try
        {
            if (objektas.toLowerCase().equals("automobili")){
                System.out.println("Iveskite automobilio numeri:");
                String nr = _scan.nextLine();

                if(nr.length() > 12){
                    System.out.println("Numeris per ilgas. Patikrinkite ar įvedėte teisingą numerį");
                }
                else if(InputValidator.isValidInput(nr)){
                    if(InputValidator.autoNumerisExist(nr, manager)) {
                        List<List> automobilis = manager.getConcreteAuto(nr);
                        System.out.println("Rodomas automobilis:");
                        System.out.println("Numeris: " + automobilis.get(0).get(1));
                        System.out.println("Tipas: " + automobilis.get(0).get(0));
                        System.out.println("Vairuotojas: " + automobilis.get(0).get(2));
                        System.out.println("");
                    }
                    else{
                        System.out.println("Automobilio tokiu numeriu nėra");
                    }
                }
            }
            else if(objektas.toLowerCase().equals("finansus")) {
                String VairuotojoAK = Selector.selectVairuotojoAK(manager, "Įveskite vairuotojo, kurio finansus norite perziureti, numerį:");
                List<List> vairuotojoFinansai = manager.getConcreteFinansai(VairuotojoAK);
                if(vairuotojoFinansai.size() == 0)
                {
                    System.out.println("Vairuotojas neturi rezervaciju");
                }
                else{
                    System.out.println("Rodomi " + vairuotojoFinansai.get(0).get(0) + " " + vairuotojoFinansai.get(0).get(1)
                            + " (AK " + vairuotojoFinansai.get(0).get(2) + ") finansai:");
                    System.out.println("Likutis: " + vairuotojoFinansai.get(0).get(3));
                    System.out.println("Bendra rezervaciju kaina: " + vairuotojoFinansai.get(0).get(4));
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
