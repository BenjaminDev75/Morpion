package Vue;

import Controleur.Morpion;

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

        this.setTitle("Controleur.Morpion");
        this.setBounds(100, 100, 1500, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setLayout(null);
        this.setResizable(false);

        // Menu play / settings
        this.panelForm.setBackground(Color.DARK_GRAY);
        this.panelForm.setLayout(new GridLayout(2, 1));
        this.panelForm.setBounds(500, 360, 500, 200);
        this.panelForm.add(btPlay);
        this.panelForm.add(btSettings);
        this.add(panelForm);

        this.btPlay.addActionListener(this);
        this.btSettings.addActionListener(this);

        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btSettings){
            Morpion.setVisibleMenu(false);
            Morpion.createMorpionSettingsVueGenerale(true);

        }

    }
}
