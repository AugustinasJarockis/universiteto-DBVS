package Actions;

import Main.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class Registracija {
    private static Scanner _scan = new Scanner(System.in);

    public static void Registruoti(SQLManager manager) throws EmptySelectionException
    {
        System.out.println("Pasirinkite ka norite uzregistruoti:\n automobili  rezervacija");
        String objektas = _scan.nextLine();

        try
        {
            if (objektas.toLowerCase().equals("automobili")){
                System.out.println("Iveskite automobilio numeri:");
                String nr = _scan.nextLine();

                if(nr.length() > 12){
                    System.out.println("Numeris per ilgas. Patikrinkite ar ivedete teisinga numeri");
                }
                else if(InputValidator.isValidInput(nr)){
                    if(InputValidator.autoNumerisExist(nr, manager)) {
                        System.out.println("Automobilis tokiu numeriu jau priregistruotas");
                    }
                    else{
                        System.out.println("Numeris priimtas\n Iveskite automobilio tipa:");
                        String tipas = _scan.nextLine();
                        if(InputValidator.isValidInput(tipas)){
                            System.out.println("Tipas priimtas");
                            String vairuotojas = Selector.selectVairuotojoAK(manager, "Iveskite vairuotojo, kuri norite prideti, numeri:");
                            System.out.println("Issaugoma automobilio informacija...");
                            manager.insertAuto(nr, tipas, vairuotojas);
                            System.out.println("Automobilis uzregistruotas");
                        }
                    }
                }
            }
            else if (objektas.toLowerCase().equals("rezervacija")){
                if(!InputValidator.anyVietaExist(manager))
                {
                    throw new EmptySelectionException("Aikštelėje nėra nei vienos vietos. Pridėkite bent vieną ir bandykite vėliau");
                }
                BigDecimal kaina = scanBigDecimal("Įveskite rezervacijos kainą:");
                int pradMetai   = scanInt("Įveskite pradžios metus:");
                int pradMenuo   = scanConstrainedInt("Įveskite pradžios mėnesį:", 1, 12);
                int pradDiena   = scanConstrainedInt("Įveskite pradžios dieną:", 1, 31);
                int pradValanda = scanConstrainedInt("Įveskite pradžios valandą:", 0, 23);
                int pradMinutes = scanConstrainedInt("Įveskite pradžios minutę:", 0, 59);

                int pabMetai = scanInt("Įveskite pabaigos metus:");
                int pabMenuo = scanConstrainedInt("Įveskite pabaigos mėnesį:", 1, 12);
                int pabDiena = scanConstrainedInt("Įveskite pabaigos dieną:", 1, 31);
                int pabValanda = scanConstrainedInt("Įveskite pabaigos valandą:", 0, 23);
                int pabMinutes = scanConstrainedInt("Įveskite pabaigos minutę:", 0, 59);

                String pradzia = "" + pradMetai + "-" + pradMenuo + "-" + pradDiena + " " + pradValanda + ":" + pradMinutes + ":00";
                String pabaiga = "" + pabMetai + "-" + pabMenuo + "-" + pabDiena + " " + pabValanda + ":" + pabMinutes + ":00";
                int vieta;
                while (true){
                    vieta = scanInt("Įveskite rezervuojamą vietą:");
                    if(InputValidator.vietaExist(vieta, manager)){
                        break;
                    }
                    else{
                        System.out.println("Tokia vieta neegzistuoja");
                    }
                }

                List<String> automobiliuNr = Selector.selectMultipleAuto(manager, "Įveskite numerius (kairiausiasis stulpelis) automobilių, kuriuos norite priskirti rezervacijai:");
                System.out.println("Visi duomenys priimti. Registruojama rezervacija...");
                manager.insertRezervacija(kaina, pradzia, pabaiga, vieta, automobiliuNr);
                System.out.println("Rezervacija priimta");
            }
            else{
                System.out.println("Neatpazintas registruojamas objektas " + objektas);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Netikėta klaida bandant užregistruoti objektą");
            System.out.println("Klaidos pranešimas: " + e.getMessage());
        }
    }

    private static BigDecimal scanBigDecimal(String msg){
        while(true){
            System.out.println(msg);
            try{
                return _scan.nextBigDecimal();
            } catch (InputMismatchException ime){
                _scan.nextLine();
                System.out.println("Įveskite SKAIČIŲ");
            }

        }
    }
    private static int scanInt(String msg){
        while(true){
            System.out.println(msg);
            try{
                return _scan.nextInt();
            } catch (InputMismatchException ime){
                _scan.nextLine();
                System.out.println("Įveskite SKAIČIŲ");
            }

        }
    }
    private static int scanConstrainedInt(String msg, int min, int max){
        while(true){
            System.out.println(msg);
            try{
                int value = _scan.nextInt();
                if(value <= max && value >= min){
                    return value;
                }
                else {
                    System.out.println("Skaičius turi būti tarp " + min + " ir " + max);
                }
            } catch (InputMismatchException ime){
                _scan.nextLine();
                System.out.println("Įveskite SKAIČIŲ");
            }

        }
    }
}
