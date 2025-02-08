package Vue;

import Controleur.Morpion;
import Vue.Settings.MorpionPanelTheme;
import Vue.Settings.MorpionThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MorpionMenu extends JFrame implements ActionListener, MorpionThemeManager.ColorChangeListener {

    private JButton btPlay = new JButton("Play");
    private JButton btSettings = new JButton("Settings");
    private JPanel panelForm = new JPanel();

    public MorpionMenu() {

        this.setTitle("Morpion");
        this.setBounds(100, 100, 1500, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(MorpionThemeManager.getBackgroundColor());// Synchronisé avec le fond global
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Menu play / settings
        this.panelForm.setBackground(MorpionThemeManager.getBackgroundColor());
        this.panelForm.setLayout(new GridLayout(2, 1));
        this.panelForm.setBounds(500, 360, 500, 200);
        this.panelForm.add(btPlay);
        this.btPlay.setBackground(Color.WHITE);
        this.panelForm.add(btSettings);
        this.btSettings.setBackground(Color.WHITE);
        this.add(panelForm);

        this.btPlay.addActionListener(this);
        this.btSettings.addActionListener(this);

        // Enregistrez cette fenêtre en tant qu'écouteur des changements de couleur
        MorpionThemeManager.addColorChangeListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btSettings) {
            // Masquez cette fenêtre et ouvrez les paramètres
            Morpion.setVisibleMenu(false);
            Morpion.createMorpionSettingsVueGenerale(true);
        }

        if (e.getSource() == btPlay) {
            Morpion.setVisibleMenu(false);
            Morpion.createPlayMenu(true);
        }
    }

    @Override
    public void onColorChange(Color newColor) {
        // Mettre à jour la couleur de fond dynamique
        this.getContentPane().setBackground(newColor);
        panelForm.setBackground(newColor);

        // Redessiner la fenêtre
        this.repaint();
        this.revalidate();
    }
}