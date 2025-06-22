package Main;

import java.awt.datatransfer.StringSelection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Selector {
    private static Scanner _scan = new Scanner(System.in);

    public static String selectVairuotojoAK(SQLManager manager, String message) throws SQLException, EmptySelectionException
    {
        List<List> vairuotojai = manager.getNumberedVairuotojaiId();
        if(vairuotojai.size() == 0) {
            throw new EmptySelectionException("Nera priregistruotu vairuotoju. Priregistruokite bent viena ir bandykite is naujo");
        }
        System.out.println("Esami vairuotojai:");
        List<String> tablenames = new ArrayList<String>();
        tablenames.add("Nr");
        tablenames.add("Vardas");
        tablenames.add("Pavarde");
        tablenames.add("AK");

        int vairuotojas;
        while(true) {
            TableDisplayer.Display(vairuotojai, tablenames);
            System.out.println(message);
            try
            {
                vairuotojas = _scan.nextInt();
                if (vairuotojas > 0 && vairuotojas <= vairuotojai.size()) {
                    return (String)vairuotojai.get(vairuotojas - 1).get(3);
                } else {
                    System.out.println("Įveskite vieną iš skaičių tarp 1 ir " + vairuotojai.size());
                }
            }
            catch (InputMismatchException ime)
            {
                _scan.nextLine();
                System.out.println("Įveskite SKAIČIŲ");
            }
        }
    }

    public static int selectRezervacijosNr(SQLManager manager, String message) throws SQLException, EmptySelectionException
    {
        List<List> rezervacijos = manager.getAllRezervacijos();
        if(rezervacijos.size() == 0) {
            throw new EmptySelectionException("Nėra registruotų rezervacijų. Priregistruokite bent vieną ir bandykite iš naujo");
        }
        System.out.println("Esamos rezervacijos:");
        List<String> tablenames = new ArrayList<String>();
        tablenames.add("Nr");
        tablenames.add("Kaina");
        tablenames.add("Pradžia");
        tablenames.add("Pabaiga");
        tablenames.add("Vieta");

        int rezervacija;
        while(true) {
            TableDisplayer.Display(rezervacijos, tablenames);
            System.out.println(message);
            try
            {
                rezervacija = _scan.nextInt();
                if (rezervacija > 0 && rezervacija <= rezervacijos.size()) {
                    return rezervacija;
                } else {
                    System.out.println("Įveskite vieną iš skaičių tarp 1 ir " + rezervacijos.size());
                }
            }
            catch (InputMismatchException ime)
            {
                _scan.nextLine();
                System.out.println("Įveskite SKAIČIŲ");
            }
        }
    }

    public static List<String> selectMultipleAuto(SQLManager manager, String message) throws SQLException, EmptySelectionException{
        List<List> allAuto = manager.getNumberedAllAuto();
        if(allAuto.size() == 0) {
            throw new EmptySelectionException("Nėra priregistruotų automobilių. Priregistruokite bent vieną ir bandykite iš naujo");
        }
        System.out.println("Esami automobiliai:");
        List<String> tablenames = new ArrayList<String>();
        tablenames.add("Nr");
        tablenames.add("Tipas");
        tablenames.add("Numeris");
        tablenames.add("Vairuotojas");

        List<String> numeriai = new ArrayList<String>();
        int numeris;
        TableDisplayer.Display(allAuto, tablenames);
        System.out.println(message);
        String vairuotojoAK = "";
        while(true) {
            try
            {
                numeris = _scan.nextInt();
                if(numeris < 0)
                {
                    System.out.println("Visi numeriai priimti");
                    break;
                }
                if (numeris > 0 && numeris <= allAuto.size()) {
                    if(numeriai.size() == 0) {
                        vairuotojoAK = (String)allAuto.get(numeris - 1).get(3);
                    }
                    if(!vairuotojoAK.equals((String)allAuto.get(numeris - 1).get(3))){
                        System.out.println("Visi automobiliai turi priklausyti tam pačiam vairuotojui");
                    }
                    else if(numeriai.contains((String)allAuto.get(numeris - 1).get(2))){
                        System.out.println("Šis automobilis jau pridėtas");
                    }
                    else{
                        System.out.println("Automobilis priimtas");
                        numeriai.add((String)allAuto.get(numeris - 1).get(2));
                    }
                } else {
                    System.out.println("Įveskite vieną iš skaičių tarp 1 ir " + allAuto.size());
                }
            }
            catch (InputMismatchException ime)
            {
                _scan.nextLine();
                System.out.println("Įveskite SKAIČIŲ");
            }
        }
        return numeriai;
    }
}
