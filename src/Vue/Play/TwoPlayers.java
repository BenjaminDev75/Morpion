package Vue.Play;

import Controleur.Morpion;
import Vue.Settings.MorpionThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class TwoPlayers extends JFrame implements ActionListener {

    private static final int SIZE = 3; // Taille de la grille (3x3)
    private JButton[][] gridButtons = new JButton[SIZE][SIZE];
    private boolean isPlayer1Turn = true; // Tour du joueur 1 (true pour "X", false pour "O")
    private JLabel statusLabel = new JLabel();
    private int movesCount = 0; // Pour compter le nombre de tours
    private int joueur1Score = 0; // Score du joueur 1
    private int joueur2Score = 0; // Score du joueur 2
    private JLabel scoreLabel = new JLabel();

    private String player1Name;
    private String player2Name;

    public TwoPlayers(String player1Name, String player2Name) {

        this.player1Name = player1Name;
        this.player2Name = player2Name;

        // Configuration de la fenêtre
        this.setTitle("Morpion - " + player1Name + " VS " + player2Name);
        this.setSize(600, 750); // Ajustez la taille de la fenêtre pour inclure tous les composants
        this.getContentPane().setBackground(MorpionThemeManager.getBackgroundColor());// Synchronisé avec le fond global
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        // Panneau principal pour la grille (centré)
        JPanel gridWrapperPanel = new JPanel(); // Panneau pour centrer la grille
        gridWrapperPanel.setLayout(new GridBagLayout()); // Centrer la grille dans la fenêtre
        gridWrapperPanel.setBackground(MorpionThemeManager.getBackgroundColor());// Synchronisé avec le fond global

        JPanel gridPanel = new JPanel(); // Panneau contenant la grille
        gridPanel.setLayout(new GridLayout(SIZE, SIZE));
        gridPanel.setPreferredSize(new Dimension(400, 400)); // Fixe la taille de la grille
        gridPanel.setBackground(MorpionThemeManager.getBackgroundColor());// Synchronisé avec le fond global

        // Initialisation de la grille
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gridButtons[i][j] = new JButton("");
                gridButtons[i][j].setFont(new Font("Arial", Font.BOLD, 36));
                gridButtons[i][j].setPreferredSize(new Dimension(100, 100)); // Taille des boutons
                gridButtons[i][j].setFocusPainted(false);
                gridButtons[i][j].addActionListener(this);
                gridPanel.add(gridButtons[i][j]);
            }
        }

        gridWrapperPanel.add(gridPanel); // Ajouter la grille dans le panneau centré
        this.add(gridWrapperPanel, BorderLayout.CENTER); // Ajouter au centre de la fenêtre principale

        // Zone pour afficher le statut et le score ensemble en bas
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(2, 1)); // 2 lignes : une pour le statut, l'autre pour le score
        statusPanel.setBackground(MorpionThemeManager.getBackgroundColor());// Synchronisé avec le fond global


// Configurer le label du statut
        statusLabel = new JLabel(player1Name + " (X), à vous de jouer !");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        statusPanel.add(statusLabel);

// Configurer le label du score
        scoreLabel = new JLabel("Score - " + player1Name + ": 0 | " + player2Name + ": 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        statusPanel.add(scoreLabel);

// Ajouter le panneau du statut et score
        this.add(statusPanel, BorderLayout.NORTH); // Ajout au haut de la fenêtre

        // === Modification majeure pour les boutons ===

        // Panneau avec les boutons (Réinitialiser et Menu)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2, 10, 10)); // Espacement horizontal entre les boutons
        bottomPanel.setBackground(MorpionThemeManager.getBackgroundColor());// Synchronisé avec le fond global

        // Bouton pour réinitialiser le jeu
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 16));
        resetButton.addActionListener(e -> resetGame());
        bottomPanel.add(resetButton);

        // Bouton pour revenir au menu
        JButton menuButton = new JButton("Menu");
        menuButton.setFont(new Font("Arial", Font.PLAIN, 16));
        menuButton.addActionListener(e -> returnToMenu());
        bottomPanel.add(menuButton);

        // Encapsulation dans un panneau parent avec marges
        JPanel bottomWrapperPanel = new JPanel(new BorderLayout());
        bottomWrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10)); // Marges : haut, gauche, bas, droite
        bottomWrapperPanel.add(bottomPanel, BorderLayout.CENTER);
        bottomWrapperPanel.setBackground(MorpionThemeManager.getBackgroundColor());// Synchronisé avec le fond global

        // Ajouter le panneau encapsulé à la fenêtre
        this.add(bottomWrapperPanel, BorderLayout.PAGE_END);

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
            statusLabel.setText(player2Name + " (O), à vous de jouer !");
        } else {
            clickedButton.setText("O");
            statusLabel.setText(player1Name + " (X), à vous de jouer !");
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
            // Afficher un message de victoire et mettre à jour les scores
            if (winner.equals(player1Name + " (X)")) {
                joueur1Score++;
            } else {
                joueur2Score++;
            }
            updateScoreLabel();
            JOptionPane.showMessageDialog(this, "Félicitations ! " + winner + " a gagné !");
            resetGame(); // Réinitialise la grille
        } else if (movesCount == SIZE * SIZE) {
            // Match nul (toutes les cases sont remplies)
            JOptionPane.showMessageDialog(this, "Match nul ! Aucun gagnant.");
            resetGame(); // Réinitialise la grille
        }
    }

    /**
     * Met à jour le label du score
     */
    private void updateScoreLabel() {
        scoreLabel.setText("Score - " + player1Name + ": " + joueur1Score + " | " + player2Name + ": " + joueur2Score);
    }

    /**
     * Réinitialise le jeu
     */
    private void resetGame() {
        isPlayer1Turn = true;
        movesCount = 0;
        statusLabel.setText(player1Name + " (X), à vous de jouer !");

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
                return gridButtons[i][0].getText().equals("X") ? player1Name + " (X)" : player2Name + " (O)";
            }
        }

        // Vérifier les colonnes
        for (int i = 0; i < SIZE; i++) {
            if (!gridButtons[0][i].getText().equals("") &&
                    gridButtons[0][i].getText().equals(gridButtons[1][i].getText()) &&
                    gridButtons[0][i].getText().equals(gridButtons[2][i].getText())) {
                return gridButtons[0][i].getText().equals("X") ? player1Name + " (X)" : player2Name + " (O)";
            }
        }

        // Vérifier la diagonale principale
        if (!gridButtons[0][0].getText().equals("") &&
                gridButtons[0][0].getText().equals(gridButtons[1][1].getText()) &&
                gridButtons[0][0].getText().equals(gridButtons[2][2].getText())) {
            return gridButtons[0][0].getText().equals("X") ? player1Name + " (X)" : player2Name + " (O)";
        }

        // Vérifier la diagonale secondaire
        if (!gridButtons[0][2].getText().equals("") &&
                gridButtons[0][2].getText().equals(gridButtons[1][1].getText()) &&
                gridButtons[0][2].getText().equals(gridButtons[2][0].getText())) {
            return gridButtons[0][2].getText().equals("X") ? player1Name + " (X)" : player2Name + " (O)";
        }

        // Pas de gagnant
        return null;
    }

    private void saveGameToFile() {
        String directoryPath = "src/Historique/"; // Répertoire souhaité
        String fileName = directoryPath + "historique_partie.txt"; // Nom du fichier où enregistrer l'historique

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Récupérer la date/heure actuelle
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String currentDateTime = now.format(formatter);

            // Écrire les détails de la partie
            writer.write("Mode de jeu : 2 Joueur");
            writer.newLine();
            writer.write("Date de la partie : " + currentDateTime);
            writer.newLine();
            writer.write("Score - " + player1Name + " : " + joueur1Score + " | " + player2Name + " : " + joueur2Score);
            writer.newLine();

            writer.write("======================================");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde de la partie.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Fonction appelée lors du retour au menu.
     */
    private void returnToMenu() {
        // Sauvegarder les données de la partie avant de revenir au menu
        saveGameToFile();

        // Fermer cette fenêtre
        this.dispose();

        // Lancer le menu principal
        Morpion.createPlayMenu(true);
    }
}