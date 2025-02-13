package Vue.Play;

import Vue.Settings.MorpionThemeManager;
import Controleur.Morpion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class PlayMenu extends JFrame implements ActionListener{
    private JButton btOnePlayer= new JButton("One Player");
    private JButton btTwoPlayers = new JButton("Two Players");
    private JButton btBack = new JButton("Back to Menu");
    private JPanel panelForm = new JPanel();
    private JPanel panelBack = new JPanel();
    private JPanel panelHistorique = new JPanel();
    private JButton btHistorique = new JButton("Historique");

    public PlayMenu() {

        this.setTitle("Play");
        this.setBounds(100, 100, 1500, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(MorpionThemeManager.getBackgroundColor());// Synchronisé avec le fond global
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        this.panelBack.setBackground(MorpionThemeManager.getBackgroundColor());
        this.panelBack.setLayout(new GridLayout(1, 2));
        this.panelBack.setBounds(15, 15, 250, 50);
        this.panelBack.add(btBack);
        this.btBack.setBackground(Color.WHITE);
        this.add(panelBack);

        // Menu play / settings
        this.panelForm.setBackground(MorpionThemeManager.getBackgroundColor());
        this.panelForm.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipady = 20;
        this.panelForm.add(btOnePlayer, gbc);
        this.btOnePlayer.setBackground(Color.WHITE);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipady = 20;
        this.panelForm.add(btTwoPlayers, gbc);
        this.btTwoPlayers.setBackground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        this.panelForm.add(btHistorique, gbc);
        this.btHistorique.setBackground(Color.WHITE);

        this.panelForm.setBounds(500, 360, 550, 200);
        this.add(panelForm);

        this.btOnePlayer.addActionListener(this);
        this.btTwoPlayers.addActionListener(this);
        this.btBack.addActionListener(this);
        this.btHistorique.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btHistorique) {
            if (e.getSource() == btHistorique) {
                try {
                    File historiqueFile = new File("src/Historique/historique_partie.txt");

                    if (!historiqueFile.exists()) {
                        JOptionPane.showMessageDialog(this, "Le fichier historique_partie.txt n'existe pas.",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(historiqueFile);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Impossible d'ouvrir le fichier historique.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }

        }

        if(e.getSource() == btBack){
            Morpion.setVisiblePlayMenu(false);
            Morpion.setVisibleMenu(true);
        }

        if (e.getSource() == btTwoPlayers){
            Morpion.setVisiblePlayMenu(false);
            Morpion.createTwoPlayer(true);
        }

        if(e.getSource() == btOnePlayer){
            Morpion.setVisiblePlayMenu(false);
            Morpion.createOnePlayer(true);
        }

    }
}
