package Main;

import Actions.*;

import java.util.Scanner;

public class UI {
    private SQLManager _manager = new SQLManager();
    private Scanner _scan = new Scanner(System.in);

    public void Begin(){
        System.out.println("Sveiki prisijungę į aikštelės duomenų valdymo sistemą");

        while(true) {
            System.out.println("Galimi veiksmai:\n registruoti  rasti  trinti  keisti  rodyti  apmoketi  baigti");
            String input = _scan.nextLine();
            try
            {
                if(input.toLowerCase().equals("baigti")){
                    break;
                }
                else if(input.toLowerCase().equals("rodyti")) {
                    Rodymas.Rodyti(_manager);
                }
                else if(input.toLowerCase().equals("registruoti")) {
                    Registracija.Registruoti(_manager);
                }
                else if(input.toLowerCase().equals("rasti")) {
                    Radimas.Rasti(_manager);
                }
                else if(input.toLowerCase().equals("keisti")) {
                    Keitimas.Keisti(_manager);
                }
                else if(input.toLowerCase().equals("trinti")) {
                    Trynimas.Trinti(_manager);
                }
                else if(input.toLowerCase().equals("apmoketi")){
                    Apmokejimas.Apmoketi(_manager);
                }
                else{
                    System.out.println("Neatpažintas veiksmas");
                }
            } catch (EmptySelectionException e)
            {
                System.out.println(e.reasonMessage);
            }
        }
    }

    public void Close()
    {
        try{
            _manager.Disconnect();
            System.out.println("Atsijungta sėkmingai");
        } catch (Exception e){
            System.out.println("Įvyko klaida atsijungiant");
            System.out.println(e.getMessage());
        }
    }
}