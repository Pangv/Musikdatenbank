package de.lebk.musikdatenbank.datenhaltungsschicht;

import de.lebk.musikdatenbank.fachschicht.ILiedSpeicher;
import de.lebk.musikdatenbank.fachschicht.Lied;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author sopaetzel
 */
public class DbZugriff{

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
