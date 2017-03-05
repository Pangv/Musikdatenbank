package de.lebk.musikdatenbank.datenhaltungsschicht;

import de.lebk.musikdatenbank.fachschicht.Lied;

import java.util.ArrayList;

/**
 * @author sopaetzel
 */
public class Global {

    private static Global instance = new Global();
    private ArrayList<Lied> lieder;
    private int position;


    private Global() {
        lieder = new ArrayList<>();
    }

    public static Global getInstance() {
        return instance;
    }


    public ArrayList<Lied> getLieder() {
        return lieder;
    }

    public void setLieder(ArrayList<Lied> lieder) {
        this.lieder = lieder;
    }

    public void speichereLied(Lied lied) {

    }

    public void aendereLied(Lied lied) {

    }

    public void loescheLied(Lied lied) {
        this.lieder.remove(lied);
    }
}
