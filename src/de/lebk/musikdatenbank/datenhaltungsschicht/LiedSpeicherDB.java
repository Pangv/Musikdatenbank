package de.lebk.musikdatenbank.datenhaltungsschicht;

import de.lebk.musikdatenbank.fachschicht.ILiedSpeicher;
import de.lebk.musikdatenbank.fachschicht.Lied;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author sopaetzel
 */
public class LiedSpeicherDB implements ILiedSpeicher {

    private DbZugriff db = DbZugriff.getZugriffsObjekt();

    @Override
    public ArrayList<Lied> getLieder() throws SQLException {
        ArrayList<Lied> lieder = new ArrayList<>();
        String sql = "SELECT * FROM lied";
        ResultSet rs = db.leseDaten(sql);
        System.out.println("Result-Set: ");
        System.out.println("==============\n");
        while (rs.next()) {
            System.out.printf("%s, %s\n", rs.getString(1),
                    rs.getString(2));
            lieder.add(new Lied(rs.getString(1), rs.getString(2)));
            LiedSpeicherGlobal.getInstance().setLieder(lieder);
        }
        System.out.println(/*break*/);
        return lieder;
    }

    @Override
    public Lied getLied(String liedname) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM lied WHERE lied = '" + liedname + "';";
        ResultSet rs = db.leseDaten(sql);
        if (rs.isBeforeFirst()){
            rs.next();
            return new Lied(rs.getString(1), rs.getString(2));
        }else {
            return null;
        }

    }

    @Override
    public boolean loescheLied(Lied lied) throws SQLException, ClassNotFoundException {
        boolean isSuccessful;
        db.oeffneDb();
        String sql = "DELETE FROM lied WHERE lied = '" + lied.getLiedname()
                + "' interpret = '" + lied.getInterpret() + "';";
        isSuccessful = db.aendereDaten(sql);
        db.schliesseDb();
        return isSuccessful;
    }

    @Override
    public boolean aendereLied(Lied lied) throws SQLException, ClassNotFoundException {
        boolean isSuccessful;
        db.oeffneDb();
        String sql = "UPDATE lied SET lied = '" + lied.getLiedname() + "', interpret = '"
                + lied.getInterpret() + "' WHERE lied = '" + lied.getLiedname() + "';";
        isSuccessful = db.aendereDaten(sql);
        db.schliesseDb();
        return isSuccessful;
    }

    @Override
    public boolean speichereLied(Lied lied) throws SQLException, ClassNotFoundException {
        boolean isSuccessful;
        db.oeffneDb();
        String sql = "MERGE INTO lied VALUES('" + lied.getLiedname() + "', '" + lied.getInterpret() + "');";
        isSuccessful = db.aendereDaten(sql);
        db.schliesseDb();
        return isSuccessful;
    }
}
