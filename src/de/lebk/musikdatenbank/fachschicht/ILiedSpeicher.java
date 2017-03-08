package de.lebk.musikdatenbank.fachschicht;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author sopaetzel
 */
public interface ILiedSpeicher {

    ArrayList<Lied> getLieder() throws SQLException;
    Lied getLied(String liedname) throws SQLException, ClassNotFoundException;
    boolean speichereLied(Lied lied) throws SQLException, ClassNotFoundException;
    boolean aendereLied(Lied lied) throws SQLException, ClassNotFoundException;
    boolean loescheLied(Lied lied) throws SQLException, ClassNotFoundException;


}