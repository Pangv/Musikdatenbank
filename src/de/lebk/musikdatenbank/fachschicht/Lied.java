package de.lebk.musikdatenbank.fachschicht;

/**
 * @author sopaetzel
 */
public class Lied {

    private String liedname;
    private String interpret;

    public Lied(String liedname, String interpret) {
        this.liedname = liedname;
        this.interpret = interpret;
    }

    public String getLiedname() {
        return liedname;
    }

    public void setLiedname(String liedname) {
        this.liedname = liedname;
    }

    public String getInterpret() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }
}
