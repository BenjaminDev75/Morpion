package Vue.Play;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class OnePlayer extends JFrame implements ActionListener {

    private static final int SIZE = 3; // Taille de la grille (3x3)
    private JButton[][] gridButtons = new JButton[SIZE][SIZE];
    private boolean isPlayer1Turn = true; // True = joueur humain (X), False = ordinateur (O)
    private JLabel statusLabel = new JLabel("Joueur 1 (X) à vous de jouer !");
    private int movesCount = 0; // Pour compter le nombre de tours
    private Random random = new Random(); // Utilisé pour le choix aléatoire de l'ordinateur

    public OnePlayer() {
        // Configuration de la fenêtre
        this.setTitle("Morpion - 1 Joueur");
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
        if (!clickedButton.getText().equals("") || !isPlayer1Turn) {
            return;
        }

        // Joueur humain (X) joue
        clickedButton.setText("X");
        movesCount++;
        isPlayer1Turn = false;
        statusLabel.setText("Ordinateur (O) est en train de jouer...");

        // Vérifier si quelqu'un a gagné ou si c'est une égalité
        if (checkGameState()) {
            return;
        }

        // Ordinateur joue automatiquement après un court délai
        Timer timer = new Timer(1000, event -> {
            computerMove();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Déplace aléatoirement pour l'ordinateur
     */
    private void computerMove() {
        // Choisit une case vide au hasard
        boolean played = false;
        while (!played) {
            int i = random.nextInt(SIZE);
            int j = random.nextInt(SIZE);

            if (gridButtons[i][j].getText().equals("")) {
                gridButtons[i][j].setText("O");
                movesCount++;
                played = true;
            }
        }

        // Vérifier si l'ordinateur a gagné ou si c'est une égalité
        if (!checkGameState()) {
            isPlayer1Turn = true;
            statusLabel.setText("Joueur 1 (X) à vous de jouer !");
        }
    }

    /**
     * Vérifie l'état du jeu : victoire ou égalité
     */
    private boolean checkGameState() {
        String winner = getWinner();

        if (winner != null) {
            // Afficher un message de victoire
            JOptionPane.showMessageDialog(this, "Félicitations ! " + winner + " a gagné !");
            resetGame(); // Réinitialise la grille
            return true;
        } else if (movesCount == SIZE * SIZE) {
            // Match nul (toutes les cases sont remplies)
            JOptionPane.showMessageDialog(this, "Match nul ! Aucun gagnant.");
            resetGame(); // Réinitialise la grille
            return true;
        }

        return false;
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
     * @return "Joueur 1", "Ordinateur" ou null (si pas de gagnant)
     */
    private String getWinner() {
        // Vérifier les lignes
        for (int i = 0; i < SIZE; i++) {
            if (!gridButtons[i][0].getText().equals("") &&
                    gridButtons[i][0].getText().equals(gridButtons[i][1].getText()) &&
                    gridButtons[i][0].getText().equals(gridButtons[i][2].getText())) {
                return gridButtons[i][0].getText().equals("X") ? "Joueur 1 (X)" : "Ordinateur (O)";
            }
        }

        // Vérifier les colonnes
        for (int i = 0; i < SIZE; i++) {
            if (!gridButtons[0][i].getText().equals("") &&
                    gridButtons[0][i].getText().equals(gridButtons[1][i].getText()) &&
                    gridButtons[0][i].getText().equals(gridButtons[2][i].getText())) {
                return gridButtons[0][i].getText().equals("X") ? "Joueur 1 (X)" : "Ordinateur (O)";
            }
        }

        // Vérifier la diagonale principale
        if (!gridButtons[0][0].getText().equals("") &&
                gridButtons[0][0].getText().equals(gridButtons[1][1].getText()) &&
                gridButtons[0][0].getText().equals(gridButtons[2][2].getText())) {
            return gridButtons[0][0].getText().equals("X") ? "Joueur 1 (X)" : "Ordinateur (O)";
        }

        // Vérifier la diagonale secondaire
        if (!gridButtons[0][2].getText().equals("") &&
                gridButtons[0][2].getText().equals(gridButtons[1][1].getText()) &&
                gridButtons[0][2].getText().equals(gridButtons[2][0].getText())) {
            return gridButtons[0][2].getText().equals("X") ? "Joueur 1 (X)" : "Ordinateur (O)";
        }

        // Pas de gagnant
        return null;
    }

    public static void main(String[] args) {
        new OnePlayer();
    }
}