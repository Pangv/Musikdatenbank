package de.lebk.musikdatenbank.datenhaltungsschicht;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author sopaetzel
 */
public class H2Datenbank {

    public H2Datenbank() throws SQLException {

        String table = "lied";
        Statement statement = DbZugriff.getZugriffsObjekt().getConnection().createStatement();

        //String dropTable = "DROP TABLE IF EXISTS " + table + ";";
        //statement.executeUpdate(dropTable);
        //System.out.println("Table dropped");

        String createTable = "CREATE TABLE IF NOT EXISTS " + table
                + " (lied VARCHAR(76) PRIMARY KEY NOT NULL, interpret VARCHAR(255) NOT NULL)";
        statement.executeUpdate(createTable);

        System.out.println("Table created");


        DbZugriff.getZugriffsObjekt().aendereDaten("MERGE INTO " + table + " VALUES('Delta','C2C')");
        DbZugriff.getZugriffsObjekt().aendereDaten("MERGE INTO " + table + " VALUES('Sonnentanz','Klangkarussel')");
        DbZugriff.getZugriffsObjekt().aendereDaten("MERGE INTO " + table + " VALUES('Fiction','The XX')");
        DbZugriff.getZugriffsObjekt().aendereDaten("MERGE INTO " + table + " VALUES('Sun and Moon','Above & Beyond')");

        System.out.println("Lines inserted");
        System.out.println("==============\n");


        DbZugriff.getZugriffsObjekt().getMetaInformation();
    }

}
