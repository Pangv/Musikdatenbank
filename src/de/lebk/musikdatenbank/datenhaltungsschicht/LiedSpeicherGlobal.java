package de.lebk.musikdatenbank.datenhaltungsschicht;

import de.lebk.musikdatenbank.fachschicht.ILiedSpeicher;
import de.lebk.musikdatenbank.fachschicht.Lied;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author sopaetzel
 */
public class LiedSpeicherGlobal implements ILiedSpeicher {

    private static LiedSpeicherGlobal instance = new LiedSpeicherGlobal();
    private ArrayList<Lied> lieder;
    private int position;


    private LiedSpeicherGlobal() {
        lieder = new ArrayList<>();
    }
    public static LiedSpeicherGlobal getInstance() {
        return instance;
    }
    public void setLieder(ArrayList<Lied> lieder) {
        this.lieder = lieder;
    }


    @Override
    public ArrayList<Lied> getLieder() {
        return lieder;
    }

    @Override
    public Lied getLied(String lied) {
        return null;
    }

    //insert
    @Override
    public boolean speichereLied(Lied lied) {
        lieder.add(new Lied(lied));return true;
    }

    //update
    @Override
    public boolean aendereLied(Lied lied) {
        Iterator<Lied> liedIterator = lieder.iterator();
        Lied lied1;
        while (liedIterator.hasNext()) {
            lied1 = liedIterator.next();
            if (lied1.getLiedname().equalsIgnoreCase(lied.getLiedname())) {
                lied1.setLiedname(lied.getLiedname());
                lied1.setInterpret(lied.getInterpret());
            }
        }
        return true;
    }

    //delete
    @Override
    public boolean loescheLied(Lied lied) {
        this.lieder.remove(lied);
        return true;
    }
}
