package Vue.Settings;

import javax.swing.*;
import java.awt.*;

public abstract class MorpionPanelPrincipal extends JPanel
        implements MorpionThemeManager.ColorChangeListener {

    private JLabel labelCouleur = new JLabel();

    public MorpionPanelPrincipal(String titre) {

        this.setBackground(MorpionThemeManager.getBackgroundColor());
        this.setBounds(400, 20, 500, 430);
        this.labelCouleur = new JLabel(titre);
        this.labelCouleur.setBounds(150, 30, 250, 30);
        Font font = new Font("Arial", Font.BOLD, 20);
        this.labelCouleur.setFont(font);
        this.add(this.labelCouleur);

        MorpionThemeManager.addColorChangeListener(this);


        this.setLayout(null);
        this.setVisible(false);
    }

    @Override
    public void onColorChange(Color newColor) {
        // Mettez à jour la couleur de fond lorsque la couleur partagée change
        this.setBackground(newColor);
        this.repaint();
    }


}
