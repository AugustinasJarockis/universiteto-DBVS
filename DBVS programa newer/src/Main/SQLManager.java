package Main;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLManager {
    private static String Url = "jdbc:postgresql://pgsql3.mif/studentu";
    private Connection connection;
    private String Username = "auja9067";
    private String Password = "" + ((char)(9*9 - ((3*8)/(12*2)))) + ((char)(100 - 11 * 6 + 8 * 8 - 1)) + ((char)(0x7 * (0x7 + 0x9))) + ((char)(0xa9 ^ 0xdb)) + ((char)(140180 % 1973)) + ((char)(501170 / 4358)) + ((char)(9 * 9 - 1 * 1 + 6 * 6)) + ((char)(9 * 9 + 4 * 4)) + ((char)((33 | 6) + (253 & 76))) + ((char)((197 | 22 | 13 | 71 | 145 | 41) - (205 & 473 & 232)));

    private PreparedStatement selectAllVairuotojai;
    private PreparedStatement selectAllAuto;
    private PreparedStatement selectAllRezervacijos;
    private PreparedStatement selectAllVietos;
    private PreparedStatement selectNumberedVairuotojaiID;
    private PreparedStatement selectNumberedAllAuto;
    private PreparedStatement selectConcreteVairuotojas;
    private PreparedStatement selectConcreteAuto;
    private PreparedStatement selectConcreteVieta;
    private PreparedStatement selectConcreteFinansai;
    private PreparedStatement selectLikutisIrSuma;
    private PreparedStatement selectLastRezervacijaNr;

    private PreparedStatement insertAuto;
    private PreparedStatement insertRezervacija;
    private PreparedStatement insertAutoRezervacija;

    private PreparedStatement updateLikutis;

    private PreparedStatement deleteRezervacijaAllAuto;
    private PreparedStatement deleteRezervacija;
    private PreparedStatement deleteVairuotojoRezervacijasIkiDatos;

    public SQLManager() {
        getDriver();
        getConnection();
        prepareStatements();
    }

    private void getDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Nepavyko rasti driverio");
            System.exit(1);
        }
    }

    private void getConnection() {
        try {
            connection = DriverManager.getConnection(Url, Username, Password) ;
        } catch (SQLException e) {
            System.out.println("Prisijungimas prie duomeų bazės nepavyko");
            System.exit(1);
        }
    }

    private void prepareStatements() {
        try
        {
            selectAllVairuotojai = connection.prepareStatement("SELECT * FROM auja9067.Vairuotojas");
            selectAllAuto = connection.prepareStatement("SELECT * FROM auja9067.Automobilis");
            selectAllRezervacijos = connection.prepareStatement("SELECT * FROM auja9067.Rezervacija");
            selectAllVietos = connection.prepareStatement("SELECT * FROM auja9067.Stovejimo_vieta");
            selectConcreteVairuotojas = connection.prepareStatement("SELECT * FROM auja9067.Vairuotojas " +
                    "WHERE AK = ?");
            selectConcreteAuto = connection.prepareStatement("SELECT * FROM auja9067.Automobilis " +
                    "WHERE Numeris = ?");
            selectConcreteFinansai = connection.prepareStatement(
                    "SELECT Vairuotojas.Vardas as Vardas, " +
                            "Vairuotojas.Pavarde as Pavarde, " +
                            "Vairuotojas.AK as AK, " +
                            "Vairuotojas.Likutis as Likutis, " +
                            "SUM(Rezervacija.Kaina) as Suma " +
                        "FROM auja9067.Vairuotojas, " +
                            "auja9067.Rezervacija, " +
                            "auja9067.Automobilis, " +
                            "auja9067.Automobilio_rezervacija " +
                        "WHERE Vairuotojas.AK = Automobilis.Vairuotojas " +
                            "AND Automobilio_rezervacija.Automobilis = Automobilis.Numeris " +
                            "AND Automobilio_rezervacija.Rezervacija = Rezervacija.Nr " +
                            "AND Vairuotojas.AK = ? " +
                        "GROUP BY Vairuotojas.AK"
            );
            selectConcreteVieta = connection.prepareStatement("SELECT * FROM auja9067.Stovejimo_vieta " +
                    "WHERE Nr = ?");
            selectLikutisIrSuma = connection.prepareStatement(
                    "SELECT Vairuotojas.Likutis as Likutis, " +
                            "SUM(Rezervacija.Kaina) as Suma " +
                        "FROM auja9067.Vairuotojas, " +
                            "auja9067.Rezervacija, " +
                            "auja9067.Automobilis, " +
                            "auja9067.Automobilio_rezervacija " +
                        "WHERE Vairuotojas.AK = Automobilis.Vairuotojas " +
                            "AND Rezervacija.Pabaiga < CURRENT_DATE " +
                            "AND Automobilio_rezervacija.Automobilis = Automobilis.Numeris " +
                            "AND Automobilio_rezervacija.Rezervacija = Rezervacija.Nr " +
                            "AND Vairuotojas.AK = ? " +
                        "GROUP BY Vairuotojas.AK"
            );
            selectNumberedVairuotojaiID = connection.prepareStatement(
                    "SELECT ROW_NUMBER() OVER (ORDER BY Pavarde, Vardas, AK), " +
                            "Vardas, " +
                            "Pavarde, " +
                            "AK " +
                       "FROM auja9067.Vairuotojas " +
                       "ORDER BY Pavarde, Vardas, AK"
            );
            selectNumberedAllAuto = connection.prepareStatement("SELECT ROW_NUMBER() OVER (ORDER BY Numeris)," +
                    " * FROM auja9067.Automobilis");
            selectLastRezervacijaNr = connection.prepareStatement("SELECT MAX(Rezervacija.Nr) FROM Rezervacija");

            insertAuto = connection.prepareStatement("INSERT INTO auja9067.Automobilis VALUES (?, ?, ?)");
            insertRezervacija = connection.prepareStatement("INSERT INTO auja9067.Rezervacija (Kaina, Pradzia, Pabaiga, Vieta) " +
                    "VALUES (?, TO_TIMESTAMP(?, 'YY-MM-DD HH24:MI:SS'), TO_TIMESTAMP(?, 'YY-MM-DD HH24:MI:SS'), ?)");
            insertAutoRezervacija = connection.prepareStatement("INSERT INTO auja9067.Automobilio_rezervacija VALUES (?, ?)");

            updateLikutis = connection.prepareStatement("UPDATE auja9067.Vairuotojas SET Likutis = ? WHERE AK = ?");

            deleteRezervacijaAllAuto = connection.prepareStatement("DELETE FROM auja9067.Automobilio_rezervacija " +
                    "WHERE Rezervacija = ?");
            deleteRezervacija = connection.prepareStatement("DELETE FROM auja9067.Rezervacija " +
                    "WHERE Nr = ?");
            deleteVairuotojoRezervacijasIkiDatos = connection.prepareStatement(
                    "DELETE FROM auja9067.Rezervacija " +
                        "WHERE Rezervacija.Pabaiga < CURRENT_DATE " +
                            "AND Rezervacija.Nr = ANY" +
                                "(SELECT Rezervacija.Nr " +
                                 "FROM auja9067.Vairuotojas, " +
                                    "auja9067.Rezervacija, " +
                                    "auja9067.Automobilis, " +
                                    "auja9067.Automobilio_rezervacija " +
                                 "WHERE Vairuotojas.AK = Automobilis.Vairuotojas " +
                                   "AND Automobilio_rezervacija.Automobilis = Automobilis.Numeris " +
                                   "AND Automobilio_rezervacija.Rezervacija = Rezervacija.Nr " +
                                   "AND Vairuotojas.AK = ?)"
            );
        }
        catch (SQLException e)
        {
            System.out.println("Netiketa klaida ruosiant uzklausas");
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
    }

    public List<List> getAllVairuotojai() throws SQLException
    {
        return selectStuff(selectAllVairuotojai);
    }
    public List<List> getAllAuto() throws SQLException
    {
        return selectStuff(selectAllAuto);
    }
    public List<List> getAllRezervacijos() throws SQLException
    {
        return selectStuff(selectAllRezervacijos);
    }
    public List<List> getAllVietos() throws SQLException
    {
        return selectStuff(selectAllVietos);
    }
    public List<List> getConcreteVairuotojas(String AK) throws SQLException
    {
        selectConcreteVairuotojas.setString(1, AK);
        return selectStuff(selectConcreteVairuotojas);
    }
    public List<List> getConcreteAuto(String nr) throws SQLException
    {
        selectConcreteAuto.setString(1, nr);
        return selectStuff(selectConcreteAuto);
    }
    public List<List> getConcreteFinansai(String AK) throws SQLException
    {
        selectConcreteFinansai.setString(1, AK);
        return selectStuff(selectConcreteFinansai);
    }
    public List<List> getConcreteVieta(int nr) throws SQLException
    {
        selectConcreteVieta.setInt(1, nr);
        return selectStuff(selectConcreteVieta);
    }
    public List<BigDecimal> getLikutisIrSuma(String Vairuotojas) throws SQLException
    {
        selectLikutisIrSuma.setString(1, Vairuotojas);
        ResultSet resultSet = selectLikutisIrSuma.executeQuery();
        List<BigDecimal> row = new ArrayList<BigDecimal>();
        while(resultSet.next()){
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row.add(resultSet.getBigDecimal(i));
            }
        }
        return row;
    }
    public List<List> getNumberedVairuotojaiId() throws SQLException
    {
        return selectStuff(selectNumberedVairuotojaiID);
    }
    public List<List> getNumberedAllAuto() throws SQLException
    {
        return selectStuff(selectNumberedAllAuto);
    }
    public void insertAuto(String numeris, String tipas, String vairuotojas) throws SQLException
    {
       insertAuto.setString(1, tipas);
       insertAuto.setString(2, numeris);
       insertAuto.setString(3, vairuotojas);
       insertAuto.executeUpdate();
    }
    public void insertRezervacija(BigDecimal kaina, String pradzia, String pabaiga, int vieta, List<String> auto) throws SQLException
    {
        connection.setAutoCommit(false);
        try{
            insertRezervacija.setBigDecimal(1, kaina);
            insertRezervacija.setString(2, pradzia);
            insertRezervacija.setString(3, pabaiga);
            insertRezervacija.setInt(4, vieta);
            insertRezervacija.executeUpdate();

            ResultSet resultSet = selectLastRezervacijaNr.executeQuery();
            resultSet.next();
            int rezervacijos_nr = resultSet.getInt(1);
            insertAutoRezervacija.setInt(2, rezervacijos_nr);
            for(int i = 0; i < auto.size(); i++)
            {
                insertAutoRezervacija.setString(1, auto.get(i));
                insertAutoRezervacija.executeUpdate();
            }
        } catch (SQLException e){
            connection.rollback();
            System.out.println("Įvyko klaida registruojant rezervacijas. Registracija atšaukta");
            throw e;
        }
        connection.commit();
        connection.setAutoCommit(true);
    }
    public void updateLikutis(String vairuotojas, BigDecimal likutis) throws SQLException
    {
        updateLikutis.setBigDecimal(1, likutis);
        updateLikutis.setString(2, vairuotojas);
        updateLikutis.executeUpdate();
    }

    public void deleteRezervacija(int rezervacijosNr) throws SQLException
    {
        connection.setAutoCommit(false);
        try{
            deleteRezervacijaAllAuto.setInt(1, rezervacijosNr);
            deleteRezervacija.setInt(1, rezervacijosNr);
            deleteRezervacijaAllAuto.executeUpdate();
            deleteRezervacija.executeUpdate();
        } catch(SQLException e){
            connection.rollback();
            System.out.println("Įvyko klaida šalinant rezervacijas. Šalinimas atšauktas");
            throw e;
        }
        connection.commit();
        connection.setAutoCommit(true);
    }
    public void apmoketiIsLikucio(String VairuotojoAK) throws SQLException
    {
        connection.setAutoCommit(false);
        try{
            List<BigDecimal> likutisIrSuma = getLikutisIrSuma(VairuotojoAK);
            deleteVairuotojoRezervacijasIkiDatos.setString(1, VairuotojoAK);
            deleteVairuotojoRezervacijasIkiDatos.executeUpdate();
            updateLikutis(VairuotojoAK, likutisIrSuma.get(0).subtract(likutisIrSuma.get(1)));
        } catch (SQLException e){
            connection.rollback();
            System.out.println("Įvyko klaida apmokant rezervacijas. Apmokėjimas atšauktas");
            throw e;
        }
        connection.commit();
        connection.setAutoCommit(true);
    }
    private List<List> selectStuff(PreparedStatement statement) throws SQLException {
        List<List> result = new ArrayList<List>();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            List<String> row = new ArrayList<String>();
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                row.add(resultSet.getString(i));
            }
            result.add(row);
        }
        return result;
    }

    public void Disconnect() throws SQLException{
        selectAllVairuotojai.close();
        selectAllAuto.close();
        selectAllRezervacijos.close();
        selectAllVietos.close();
        selectNumberedVairuotojaiID.close();
        selectNumberedAllAuto.close();
        selectConcreteVairuotojas.close();
        selectConcreteAuto.close();
        selectConcreteVieta.close();
        selectConcreteFinansai.close();
        selectLikutisIrSuma.close();
        selectLastRezervacijaNr.close();

        insertAuto.close();
        insertRezervacija.close();
        insertAutoRezervacija.close();

        updateLikutis.close();

        deleteRezervacijaAllAuto.close();
        deleteRezervacija.close();
        deleteVairuotojoRezervacijasIkiDatos.close();

        connection.close();
    }
}