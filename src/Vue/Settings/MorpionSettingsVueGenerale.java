package Vue.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MorpionSettingsVueGenerale extends JFrame  implements ActionListener {

    private JPanel panelSettings = new JPanel();

    private JButton btBack = new JButton("Retour");
    private JButton btSave = new JButton("Sauvegarder");
    private JButton btColor = new JButton("Thème");

    // instance du panel
    private static MorpionPanelCouleur unMorpionPanelCouleur = new MorpionPanelCouleur();

    public MorpionSettingsVueGenerale() {

        //
        // Configuration de la fenêtre
        this.setTitle("Paramètre");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setLayout(null);
        this.setBounds(100, 100, 1000, 500);

// Configuration du panneau pour les boutons
        this.panelSettings.setBounds(50, 100, 300, 200); // Panneau de dimensions adaptées au contenu
        this.panelSettings.setLayout(new GridLayout(3, 1, 10, 50)); // 3 lignes, 1 colonne, espace entre les composants
        this.panelSettings.setBackground(Color.DARK_GRAY);

// Ajout des boutons dans le panneau
        this.panelSettings.add(btBack);    // Bouton "Retour"
        this.panelSettings.add(btColor);   // Bouton "Couleur"
        this.panelSettings.add(btSave);    // Bouton "Sauvegarder"

// Ajout du panneau contenant les boutons à la fenêtre principale
        this.add(this.panelSettings);

// Ajout d'écouteurs d'événements pour chaque bouton
        this.btBack.addActionListener(this);
        this.btSave.addActionListener(this);
        this.btColor.addActionListener(this);

// Rendre la fenêtre visible
        this.setVisible(true);

    }

    private void afficherPanel(int choix){
        unMorpionPanelCouleur.setVisible(false);

        switch(choix){
            case 1:
                unMorpionPanelCouleur.setVisible(true);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.btBack)
        {
            this.dispose();
        }

        String choix = e.getActionCommand();

        if(choix.equals("Thème"))
        {
            this.afficherPanel(1);
        }
    }
}
