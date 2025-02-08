package Vue.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controleur.Morpion;

public class MorpionSettingsVueGenerale extends JFrame  implements ActionListener, MorpionThemeManager.ColorChangeListener  {

    private JPanel panelSettings = new JPanel();

    private JButton btBack = new JButton("Retour");
    private JButton btColor = new JButton("Thème");

    // instance du panel
    private static MorpionPanelTheme unMorpionPanelCouleur = new MorpionPanelTheme();

    public MorpionSettingsVueGenerale() {

        // Configuration de la fenêtre
        this.setTitle("Paramètre");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setResizable(false);
        this.getContentPane().setBackground(MorpionThemeManager.getBackgroundColor());
        this.setLayout(null);
        this.setBounds(100, 100, 1000, 500);
        this.setLocationRelativeTo(null);
// Configuration du panneau pour les boutons
        this.panelSettings.setBounds(50, 150, 300, 200); // Panneau de dimensions adaptées au contenu
        this.panelSettings.setLayout(new GridLayout(3, 1, 10, 50)); // 3 lignes, 1 colonne, espace entre les composants
        this.panelSettings.setBackground(MorpionThemeManager.getBackgroundColor());

// Ajout des boutons dans le panneau
        this.panelSettings.add(btBack);    // Bouton "Retour"
        this.btBack.setBackground(Color.WHITE);
        this.panelSettings.add(btColor);   // Bouton "Couleur"
        this.btColor.setBackground(Color.WHITE);


// Ajout du panneau contenant les boutons à la fenêtre principale
        this.add(this.panelSettings);

// Ajout d'écouteurs d'événements pour chaque bouton
        this.btBack.addActionListener(this);
        this.btColor.addActionListener(this);

        this.add(unMorpionPanelCouleur);

        MorpionThemeManager.addColorChangeListener(this);

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
            Morpion.setVisibleMenu(true);
        }

        String choix = e.getActionCommand();

        if(choix.equals("Thème"))
        {
            this.afficherPanel(1);
        }
    }

    @Override
    public void onColorChange(Color newColor) {
        // Mettre à jour la couleur de fond dynamique
        this.getContentPane().setBackground(newColor);
        this.panelSettings.setBackground(newColor);



        // Propager la couleur au panneau de thème
        unMorpionPanelCouleur.setBackground(newColor);

        // Redessiner les composants
        this.repaint();
        this.revalidate();
    }

}
