package de.lebk.musikdatenbank.datenhaltungsschicht;

import de.lebk.musikdatenbank.fachschicht.Lied;

import java.util.ArrayList;
import java.util.Iterator;

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


    //insert
    public void speichereLied(Lied lied) {
        lieder.add(new Lied(lied));
    }

    //update
    public void aendereLied(Lied lied) {
        Iterator<Lied> liedIterator = lieder.iterator();
        Lied lied1;
        while (liedIterator.hasNext()){
            lied1 = liedIterator.next();
            if (lied1.getLiedname().equalsIgnoreCase(lied.getLiedname())){
                lied1.setLiedname(lied.getLiedname());
                lied1.setInterpret(lied.getInterpret());
            }
        }
    }

    //delete
    public void loescheLied(Lied lied) {
        this.lieder.remove(lied);
    }
}
