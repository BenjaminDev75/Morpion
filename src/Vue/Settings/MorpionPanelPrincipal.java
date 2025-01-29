package Vue.Settings;

import javax.swing.*;
import java.awt.*;

public abstract class MorpionPanelPrincipal extends JPanel{

    private JLabel labelCouleur = new JLabel();

    public MorpionPanelPrincipal(String titre) {

        this.setBackground(Color.CYAN);
        this.setBounds(50, 80, 500, 500);
        this.labelCouleur = new JLabel(titre);
        this.setBounds(350, 40, 250, 30);
        Font font = new Font("Arial", Font.BOLD, 20);
        this.labelCouleur.setFont(font);
        this.add(this.labelCouleur);

        this.setLayout(null);
        this.setVisible(true);
    }

}
