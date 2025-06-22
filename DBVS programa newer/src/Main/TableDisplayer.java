package Main;

import java.util.ArrayList;
import java.util.List;

public class TableDisplayer {
    public static void Display(List<List> tableData, List<String> tableNames)
    {
        List<Integer> max_lengths = new ArrayList<Integer>();

        for(int i = 0; i < tableData.get(0).size(); i++) {
            max_lengths.add(tableNames.get(i).length());
        }

        for (List<String> tableRow : tableData) {
            for(int i = 0; i < tableRow.size();  i++) {
                max_lengths.set(i, Math.max(max_lengths.get(i), tableRow.get(i).length()));
            }
        }

        String first_format = "%-" + max_lengths.get(0) + "s";
        System.out.printf(first_format, tableNames.get(0));
        for(int i = 1; i < tableNames.size(); i++)
        {
            System.out.print(" | ");
            String format = "%-" + max_lengths.get(i) + "s";
            System.out.printf(format, tableNames.get(i));
        }
        System.out.println("\n------------------------------------------------------------------------------------------------");
        for(List<String> tableRow : tableData) {
            System.out.printf(first_format, tableRow.get(0));
            for(int i = 1; i < tableRow.size(); i++) {
                System.out.print(" | ");
                String format = "%-" + max_lengths.get(i) + "s";
                System.out.printf(format, tableRow.get(i));
            }
            System.out.println("");
        }
    }
}
