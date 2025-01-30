package Vue.Play;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TwoPlayers extends JFrame implements ActionListener {

    private static final int SIZE = 3; // Taille de la grille (3x3)
    private JButton[][] gridButtons = new JButton[SIZE][SIZE];
    private boolean isPlayer1Turn = true; // Tour du joueur 1 (true pour "X", false pour "O")
    private JLabel statusLabel = new JLabel("Joueur 1 (X) à vous de jouer !");
    private int movesCount = 0; // Pour compter le nombre de tours

    public TwoPlayers() {
        // Configuration de la fenêtre
        this.setTitle("Morpion - 2 Joueurs");
        this.setSize(500, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Zone de statut
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(statusLabel, BorderLayout.NORTH);

        // Grille de jeu (panneau central)
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(SIZE, SIZE));
        this.add(gridPanel, BorderLayout.CENTER);

        // Initialisation de la grille
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gridButtons[i][j] = new JButton("");
                gridButtons[i][j].setFont(new Font("Arial", Font.BOLD, 36));
                gridButtons[i][j].setFocusPainted(false);
                gridButtons[i][j].addActionListener(this);
                gridPanel.add(gridButtons[i][j]);
            }
        }

        // Bouton pour réinitialiser le jeu
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 16));
        resetButton.addActionListener(e -> resetGame());
        this.add(resetButton, BorderLayout.SOUTH);

        // Rendre la fenêtre visible
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        // Ignorer si le bouton est déjà utilisé
        if (!clickedButton.getText().equals("")) {
            return;
        }

        // Ajouter "X" ou "O" selon le joueur en cours
        if (isPlayer1Turn) {
            clickedButton.setText("X");
            statusLabel.setText("Joueur 2 (O) à vous de jouer !");
        } else {
            clickedButton.setText("O");
            statusLabel.setText("Joueur 1 (X) à vous de jouer !");
        }

        movesCount++;
        isPlayer1Turn = !isPlayer1Turn; // Changer le joueur en cours

        // Vérifier si quelqu'un a gagné ou si c'est une égalité
        checkGameState();
    }

    /**
     * Vérifie l'état du jeu : victoire ou égalité
     */
    private void checkGameState() {
        String winner = getWinner();

        if (winner != null) {
            // Afficher un message de victoire
            JOptionPane.showMessageDialog(this, "Félicitations ! " + winner + " a gagné !");
            disableGrid(); // Désactiver la grille
        } else if (movesCount == SIZE * SIZE) {
            // Match nul (toutes les cases sont remplies)
            JOptionPane.showMessageDialog(this, "Match nul ! Aucun gagnant.");
            disableGrid();
        }
    }

    /**
     * Désactive tous les boutons de la grille
     */
    private void disableGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gridButtons[i][j].setEnabled(false);
            }
        }
    }

    /**
     * Réinitialise le jeu
     */
    private void resetGame() {
        isPlayer1Turn = true;
        movesCount = 0;
        statusLabel.setText("Joueur 1 (X) à vous de jouer !");

        // Réinitialiser chaque bouton
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gridButtons[i][j].setText("");
                gridButtons[i][j].setEnabled(true);
            }
        }
    }

    /**
     * Vérifie s'il y a un gagnant
     *
     * @return "Joueur 1", "Joueur 2" ou null (si pas de gagnant)
     */
    private String getWinner() {
        // Vérifier les lignes
        for (int i = 0; i < SIZE; i++) {
            if (!gridButtons[i][0].getText().equals("") &&
                    gridButtons[i][0].getText().equals(gridButtons[i][1].getText()) &&
                    gridButtons[i][0].getText().equals(gridButtons[i][2].getText())) {
                return gridButtons[i][0].getText().equals("X") ? "Joueur 1 (X)" : "Joueur 2 (O)";
            }
        }

        // Vérifier les colonnes
        for (int i = 0; i < SIZE; i++) {
            if (!gridButtons[0][i].getText().equals("") &&
                    gridButtons[0][i].getText().equals(gridButtons[1][i].getText()) &&
                    gridButtons[0][i].getText().equals(gridButtons[2][i].getText())) {
                return gridButtons[0][i].getText().equals("X") ? "Joueur 1 (X)" : "Joueur 2 (O)";
            }
        }

        // Vérifier la diagonale principale
        if (!gridButtons[0][0].getText().equals("") &&
                gridButtons[0][0].getText().equals(gridButtons[1][1].getText()) &&
                gridButtons[0][0].getText().equals(gridButtons[2][2].getText())) {
            return gridButtons[0][0].getText().equals("X") ? "Joueur 1 (X)" : "Joueur 2 (O)";
        }

        // Vérifier la diagonale secondaire
        if (!gridButtons[0][2].getText().equals("") &&
                gridButtons[0][2].getText().equals(gridButtons[1][1].getText()) &&
                gridButtons[0][2].getText().equals(gridButtons[2][0].getText())) {
            return gridButtons[0][2].getText().equals("X") ? "Joueur 1 (X)" : "Joueur 2 (O)";
        }

        // Pas de gagnant
        return null;
    }

    public static void main(String[] args) {
        new TwoPlayers();
    }
}