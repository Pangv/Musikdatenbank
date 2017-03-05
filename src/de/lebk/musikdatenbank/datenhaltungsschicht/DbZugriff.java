package de.lebk.musikdatenbank.datenhaltungsschicht;

import de.lebk.musikdatenbank.fachschicht.Lied;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author sopaetzel
 */
public class DbZugriff {

    private static DbZugriff zugriffsObjekt = new DbZugriff();
    private Connection connection;
    private Statement statement;

    public static DbZugriff getZugriffsObjekt() {
        return zugriffsObjekt;
    }

    public Connection getConnection() {
        return connection;
    }

    public void oeffneDb() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:h2:~/data/meinemusik";
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection(connectionString, "", "");
        statement = connection.createStatement();
    }

    public ResultSet leseDaten(String SQL) throws SQLException {
        return statement.executeQuery(SQL);
    }

    public boolean aendereDaten(String SQL) throws SQLException {
        return statement.execute(SQL);
    }

    public void schliesseDb() throws SQLException {
        connection.close();
    }

    //------------------- LIEDER --------------------------------------//
    public ArrayList<Lied> getLieder() throws SQLException {
        ArrayList<Lied> lieder = new ArrayList<>();
        String sql = "SELECT * FROM lied";
        ResultSet rs = leseDaten(sql);
        System.out.println("Result-Set: ");
        System.out.println("==============\n");
        while (rs.next()) {
            System.out.printf("%s, %s\n", rs.getString(1),
                    rs.getString(2));
            lieder.add(new Lied(rs.getString(1), rs.getString(2)));
            Global.getInstance().setLieder(lieder);
        }
        System.out.println(/*break*/);
        return lieder;
    }

    public Lied getLied(String liedname) throws SQLException {
        String sql = "SELECT * FROM lied WHERE lied = '" + liedname + "';";
        ResultSet rs = leseDaten(sql);
        rs.next();
        return new Lied(rs.getString(1), rs.getString(2));
    }

    public boolean speicherLied(Lied lied) throws SQLException, ClassNotFoundException {
        boolean isSuccessful;
        oeffneDb();
        String sql = "MERGE INTO lied VALUES('" + lied.getLiedname() + "', '" + lied.getInterpret() + "');";
        isSuccessful = aendereDaten(sql);
        schliesseDb();
        return isSuccessful;
    }

    public boolean loescheLied(Lied lied) throws SQLException, ClassNotFoundException {
        boolean isSuccessful;
        oeffneDb();
        String sql = "DELETE FROM lied WHERE lied = '" + lied.getLiedname()
                + "' interpret = '" + lied.getInterpret() + "';";
        isSuccessful = aendereDaten(sql);
        schliesseDb();
        return isSuccessful;
    }

    public boolean aenderLied(Lied lied) throws SQLException, ClassNotFoundException {
        boolean isSuccessful;
        oeffneDb();
        String sql = "UPDATE lied SET lied = '" + lied.getLiedname() + "', interpret = '"
                + lied.getInterpret() + "' WHERE lied = '"+ lied.getLiedname() +"';";
        isSuccessful = aendereDaten(sql);
        schliesseDb();
        return isSuccessful;
    }


    void getMetaInformation() throws SQLException {
        DatabaseMetaData metaData = this.connection.getMetaData();

        String[] types = {"TABLE"};

        ResultSet metaRS = metaData.getTables(null, null, "%", types);

        while (metaRS.next()) {
            System.out.println("Meta-Information: ");
            System.out.println("==============\n");
            // Catalog
            String tableCatalog = metaRS.getString(1);
            System.out.println("Catalog: " + tableCatalog);

            // Schemata
            String tableSchema = metaRS.getString(2);
            System.out.println("Tabellen-Schema: " + tableSchema);

            // Tabellennamen
            String tableName = metaRS.getString(3);
            System.out.println("Tabellen-Name: " + tableName);

            // Tabellentyp
            String tableType = metaRS.getString(4);
            System.out.println("Tabellen-Typ: " + tableType + "\n");
        }
    }

}
