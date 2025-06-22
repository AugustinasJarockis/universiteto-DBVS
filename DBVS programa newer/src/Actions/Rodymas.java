package Actions;

import Main.SQLManager;
import Main.TableDisplayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Rodymas {
    private static Scanner _scan = new Scanner(System.in);

    public static void Rodyti(SQLManager manager)
    {
        System.out.println("Pasirinkite ka rodyti:\n vairuotojus  automobilius  rezervacijas");
        String tablename =_scan.nextLine();

        try
        {
            if (tablename.toLowerCase().equals("vairuotojus")){
                List<List> vairuotojai = manager.getAllVairuotojai();
                System.out.println("Rodomas vairuotojų sąrašas:");
                List<String> tablenames = new ArrayList<String>();
                tablenames.add("Vardas");
                tablenames.add("Pavarde");
                tablenames.add("Telefonas");
                tablenames.add("Pastas");
                tablenames.add("AK");
                tablenames.add("Likutis");
                TableDisplayer.Display(vairuotojai, tablenames);
            }
            else if(tablename.toLowerCase().equals("automobilius"))
            {
                List<List> automobiliai = manager.getAllAuto();
                System.out.println("Rodomas automobilių sąrašas:");
                List<String> tablenames = new ArrayList<String>();
                tablenames.add("Tipas");
                tablenames.add("Numeris");
                tablenames.add("Vairuotojas");
                TableDisplayer.Display(automobiliai, tablenames);
            }
            else if(tablename.toLowerCase().equals("rezervacijas"))
            {
                List<List> rezervacijos = manager.getAllRezervacijos();
                System.out.println("Rodomas rezervacijų sąrašas:");
                List<String> tablenames = new ArrayList<String>();
                tablenames.add("Nr");
                tablenames.add("Kaina");
                tablenames.add("Pradžia");
                tablenames.add("Pabaiga");
                tablenames.add("Vieta");
                TableDisplayer.Display(rezervacijos, tablenames);
            }
            else{
                System.out.println("Neatpazintas reikalavimas parodyti " + tablename);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Netiketa klaida bandant paimti duomenis is duomenu bazes");
            System.out.println("Klaidos pranesimas: " + e.getMessage());
        }
    }
}
