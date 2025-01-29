package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MorpionMenu extends JFrame implements ActionListener {

    private JButton btPlay = new JButton("Play");
    private JButton btSettings = new JButton("Settings");
    private JPanel panelForm = new JPanel();



    public MorpionMenu()
    {

        this.setTitle("Morpion");
        this.setBounds(100, 100, 1500, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setLayout(null);
        this.setResizable(false);

        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
