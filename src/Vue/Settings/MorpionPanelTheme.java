package Vue.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MorpionPanelTheme extends MorpionPanelPrincipal {

    public MorpionPanelTheme() {
        super("Choix du thème du jeu :");

        this.setLayout(null);

        // Ajoutez des boutons pour les thèmes
        JButton btSombre = new JButton("Sombre");
        JButton btClair = new JButton("Clair");
        JButton btAleatoire = new JButton("Aléatoire");
        JButton btChoisir = new JButton("Choisir");

        // Définir les positions des boutons
        btSombre.setBounds(100, 100, 150, 30);
        btClair.setBounds(100, 150, 150, 30);
        btAleatoire.setBounds(100, 200, 150, 30);
        btChoisir.setBounds(100, 250, 150, 30);

        // Action pour le bouton "Sombre"
        btSombre.addActionListener(e -> {
            MorpionThemeManager.setBackgroundColor(Color.DARK_GRAY);
        });

        // Action pour le bouton "Clair"
        btClair.addActionListener(e -> {
            MorpionThemeManager.setBackgroundColor(Color.LIGHT_GRAY);
        });

        // Action pour le bouton "Aléatoire"
        btAleatoire.addActionListener(e -> {
            // Générer une couleur aléatoire
            Random random = new Random();
            Color randomColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            MorpionThemeManager.setBackgroundColor(randomColor);
        });

        // Action pour le bouton "Choisir"
        btChoisir.addActionListener(e -> {
            // Ouvrir une boîte de dialogue pour choisir une couleur
            Color newColor = JColorChooser.showDialog(this, "Choisir une couleur de fond", MorpionThemeManager.getBackgroundColor());
            if (newColor != null) {
                MorpionThemeManager.setBackgroundColor(newColor); // Mettre à jour la couleur partagée
            }
        });

        // Ajouter les boutons au panneau
        this.add(btSombre);
        btSombre.setBackground(Color.WHITE);
        this.add(btClair);
        btClair.setBackground(Color.WHITE);
        this.add(btAleatoire);
        btAleatoire.setBackground(Color.WHITE);
        this.add(btChoisir);
        btChoisir.setBackground(Color.WHITE);
    }
}