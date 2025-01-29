package Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MorpionMenu extends JFrame implements ActionListener {

    private JButton btPlay = new JButton("Play");
    private JButton btSettings = new JButton("Settings");




    public MorpionMenu() {
        super("Morpion");

        setVisible(true);
    }

    

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
