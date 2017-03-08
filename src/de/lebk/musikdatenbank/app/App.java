package de.lebk.musikdatenbank.app;

import de.lebk.musikdatenbank.darstellungsschicht.Gui;
import de.lebk.musikdatenbank.datenhaltungsschicht.DbZugriff;
import de.lebk.musikdatenbank.datenhaltungsschicht.LiedSpeicherDB;
import de.lebk.musikdatenbank.datenhaltungsschicht.LiedSpeicherGlobal;
import de.lebk.musikdatenbank.datenhaltungsschicht.H2Datenbank;
import de.lebk.musikdatenbank.fachschicht.Lied;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * @author sopaetzel
 */
public class App {



    public static void main(String[] args) {
        erstelleH2();
        Gui.main(args);


        Iterator<Lied> liedIterator = LiedSpeicherGlobal.getInstance().getLieder().iterator();
        Lied lied;
        int i = 1;
        while (liedIterator.hasNext()) {
            lied = liedIterator.next();
            System.out.println("[" + (i++) + ". Song] Titel: " + lied.getLiedname() + "\t Interpret: " + lied.getInterpret());
        }

    }


    private static void erstelleH2() {
        LiedSpeicherDB lsdb = new LiedSpeicherDB();

        try {
            DbZugriff.getZugriffsObjekt().oeffneDb();
            new H2Datenbank();
            lsdb.getLieder();
            DbZugriff.getZugriffsObjekt().schliesseDb();
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Datenbanktreiber konnte nicht gefunden werden");
        } finally {
            if (DbZugriff.getZugriffsObjekt().getConnection() != null) {
                try {
                    DbZugriff.getZugriffsObjekt().schliesseDb();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
