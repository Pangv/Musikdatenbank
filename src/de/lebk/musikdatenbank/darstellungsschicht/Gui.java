package de.lebk.musikdatenbank.darstellungsschicht;

import de.lebk.musikdatenbank.datenhaltungsschicht.DbZugriff;
import de.lebk.musikdatenbank.datenhaltungsschicht.Global;
import de.lebk.musikdatenbank.fachschicht.Lied;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.sql.SQLException;

/**
 * @author sopaetzel
 */
public class Gui {

    private JPanel pnlGui;
    private JTextField txtfLiedname;
    private JTextField txtfInterpret;
    private JButton btnSuchen;
    private JButton btnSpeichern;
    private JButton btnZurueck;
    private JButton btnVorwaerts;
    private JLabel lblInterpret;
    private JLabel lblLiedname;


    private Lied aktuellesLied;
    private int position;


    public Gui() {
        this.initActionListener();
        this.setInitialSong();

    }

    public static void main(String[] args) {
        setLookAndFeel();

        JFrame frame = new JFrame("Meine Musiksammlung");
        JPanel container = new Gui().pnlGui;
        container.setBorder(new EmptyBorder(5, 5, 2, 2));

        frame.setContentPane(container);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void initActionListener() {
        btnSuchen.addActionListener(e -> {
            String liedname = txtfLiedname.getText();

            try {
                DbZugriff.getZugriffsObjekt().oeffneDb();
                aktuellesLied = DbZugriff.getZugriffsObjekt().getLied(liedname);

                txtfInterpret.setText(aktuellesLied.getInterpret());

                DbZugriff.getZugriffsObjekt().schliesseDb();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            } finally {
                if (DbZugriff.getZugriffsObjekt().getConnection() != null) {
                    try {
                        DbZugriff.getZugriffsObjekt().schliesseDb();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }


        });
        btnSpeichern.addActionListener(e -> {
            try {
                aktuellesLied.setLiedname(txtfLiedname.getText());
                aktuellesLied.setInterpret(txtfInterpret.getText());

                DbZugriff.getZugriffsObjekt().speicherLied(aktuellesLied);

            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            Global.getInstance().speichereLied(aktuellesLied);


        });
        btnZurueck.addActionListener(e -> {
            if (position-- <= 0) {
                position = Global.getInstance().getLieder().size() - 1;
            }
            aktuellesLied = Global.getInstance().getLieder().get(position);
            txtfLiedname.setText(aktuellesLied.getLiedname());
            txtfInterpret.setText(aktuellesLied.getInterpret());
        });
        btnVorwaerts.addActionListener(e -> {
            if (position++ >= Global.getInstance().getLieder().size() - 1) {
                position = 0;
            }
            aktuellesLied = Global.getInstance().getLieder().get(position);
            txtfLiedname.setText(aktuellesLied.getLiedname());
            txtfInterpret.setText(aktuellesLied.getInterpret());
        });
    }

    private void setInitialSong() {
        this.position = 0;
        this.aktuellesLied = Global.getInstance().getLieder().get(position);
        txtfLiedname.setText(aktuellesLied.getLiedname());
        txtfInterpret.setText(aktuellesLied.getInterpret());
    }

}
