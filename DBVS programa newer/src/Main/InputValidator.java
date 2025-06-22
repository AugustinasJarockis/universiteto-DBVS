package Main;

import java.sql.SQLException;
import java.util.List;

public class InputValidator {
    public static boolean isValidInput(String input)
    {
        String symbolsToCheck = "'\"(){}\\?=<>!";
        for(int i = 0; i < symbolsToCheck.length(); i++) {
            if(input.contains("" + symbolsToCheck.charAt(i))){
                System.out.println("Ivestis negali tureti simboliu" + symbolsToCheck);
                return false;
            }
        }
        return true;
    }

    public static boolean autoNumerisExist(String nr, SQLManager manager) throws SQLException
    {
        List<List> autoList = manager.getConcreteAuto(nr);
        if(autoList.size() > 0)
            return true;
        return false;
    }

    public static boolean anyVietaExist(SQLManager manager) throws SQLException
    {
        List<List> vietaList = manager.getAllVietos();
        if(vietaList.size() > 0)
            return true;
        return false;
    }

    public static boolean vietaExist(int nr, SQLManager manager) throws SQLException
    {
        List<List> vietaList = manager.getConcreteVieta(nr);
        if(vietaList.size() > 0)
            return true;
        return false;
    }
}
