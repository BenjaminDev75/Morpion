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
import java.util.Random;

public class OnePlayer extends JFrame implements ActionListener {

    private static final int SIZE = 3; // Taille de la grille (3x3)
    private JButton[][] gridButtons = new JButton[SIZE][SIZE];
    private boolean isPlayer1Turn = true; // True = joueur humain (X), False = ordinateur (O)
    private JLabel statusLabel = new JLabel();
    private int movesCount = 0;
    private int joueurScore = 0; // Score du joueur 1
    private int ordiScore = 0; // Score de l'ordinateur
    private JLabel scoreLabel = new JLabel();
    private Random random = new Random(); // Génére des mouvements aléatoires pour l'ordinateur

    private String playerName; // Nom du joueur

    public OnePlayer(String playerName) {

        this.playerName = playerName;

        // Configuration de la fenêtre
        this.setTitle("Morpion - " + playerName + " VS Ordinateur");
        this.setSize(600, 750);
        this.getContentPane().setBackground(MorpionThemeManager.getBackgroundColor());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Panneau principal pour la grille
        JPanel gridWrapperPanel = new JPanel();
        gridWrapperPanel.setLayout(new GridBagLayout());
        gridWrapperPanel.setBackground(MorpionThemeManager.getBackgroundColor());

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(SIZE, SIZE));
        gridPanel.setPreferredSize(new Dimension(400, 400));
        gridPanel.setBackground(MorpionThemeManager.getBackgroundColor());

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

        gridWrapperPanel.add(gridPanel);
        this.add(gridWrapperPanel, BorderLayout.CENTER);

        // Panel pour afficher les statuts et le score
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(2, 1)); // Deux lignes : statut et score
        statusPanel.setBackground(MorpionThemeManager.getBackgroundColor());

        // Initialisation de l'étiquette pour le statut
        statusLabel = new JLabel(playerName + " (X), à vous de jouer !");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusPanel.add(statusLabel);

        // Initialisation du label pour le score
        scoreLabel = new JLabel("Score - " + playerName + ": 0 | Ordinateur : 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        statusPanel.add(scoreLabel);

        // Ajouter le panneau de statuts et de scores à l'interface
        this.add(statusPanel, BorderLayout.NORTH);

        // Boutons Réinitialiser et Menu
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.setBackground(MorpionThemeManager.getBackgroundColor());

        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 16));
        resetButton.addActionListener(e -> resetGame());
        bottomPanel.add(resetButton);

        JButton menuButton = new JButton("Menu");
        menuButton.setFont(new Font("Arial", Font.PLAIN, 16));
        menuButton.addActionListener(e -> returnToMenu());
        bottomPanel.add(menuButton);

        JPanel bottomWrapperPanel = new JPanel(new BorderLayout());
        bottomWrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        bottomWrapperPanel.add(bottomPanel, BorderLayout.CENTER);
        bottomWrapperPanel.setBackground(MorpionThemeManager.getBackgroundColor());

        this.add(bottomWrapperPanel, BorderLayout.PAGE_END);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        // Vérifier si la case est déjà jouée ou si ce n'est pas le tour du joueur
        if (!clickedButton.getText().equals("") || !isPlayer1Turn) {
            return;
        }

        // Joueur 1 (X) joue
        clickedButton.setText("X");
        movesCount++;
        isPlayer1Turn = false;

        // Vérifier si quelqu'un a gagné ou si la partie est terminée
        if (checkGameState()) {
            return;
        }

        // Afficher que l'ordinateur va jouer
        statusLabel.setText("Ordinateur (O) est en train de jouer...");

        // Ordinateur joue automatiquement après un court délai
        Timer timer = new Timer(1000, event -> computerMove());
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Déplace aléatoirement pour l'ordinateur
     */
    private void computerMove() {
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

        // Vérifier si l'ordinateur a gagné ou si la partie est terminée
        if (!checkGameState()) {
            isPlayer1Turn = true; // Redonne la main au joueur
            statusLabel.setText(playerName + " (X), à vous de jouer !");
        }
    }

    /**
     * Vérifie l'état du jeu : victoire ou match nul
     */
    private boolean checkGameState() {
        String winner = getWinner();

        if (winner != null) {
            if (winner.equals("Joueur 1 (X)")) {
                joueurScore++;
            } else {
                ordiScore++;
            }
            updateScoreLabel();
            JOptionPane.showMessageDialog(this, winner + " a gagné !");
            resetGame();
            return true;
        } else if (movesCount == SIZE * SIZE) {
            JOptionPane.showMessageDialog(this, "Match nul !");
            resetGame();
            return true;
        }
        return false;
    }

    /**
     * Met à jour le score affiché
     */
    private void updateScoreLabel() {
        scoreLabel.setText("Score - " + playerName + ": " + joueurScore + " | Ordinateur : " + ordiScore);
    }

    /**
     * Réinitialise la partie
     */
    private void resetGame() {
        isPlayer1Turn = true;
        movesCount = 0;
        statusLabel.setText(playerName + " (X), à vous de jouer !");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gridButtons[i][j].setText("");
            }
        }
    }

    /**
     * Vérifie s'il y a un gagnant
     */
    private String getWinner() {
        for (int i = 0; i < SIZE; i++) {
            if (!gridButtons[i][0].getText().equals("") &&
                    gridButtons[i][0].getText().equals(gridButtons[i][1].getText()) &&
                    gridButtons[i][0].getText().equals(gridButtons[i][2].getText())) {
                return gridButtons[i][0].getText().equals("X") ? "Joueur 1 (X)" : "Ordinateur (O)";
            }
        }

        for (int i = 0; i < SIZE; i++) {
            if (!gridButtons[0][i].getText().equals("") &&
                    gridButtons[0][i].getText().equals(gridButtons[1][i].getText()) &&
                    gridButtons[0][i].getText().equals(gridButtons[2][i].getText())) {
                return gridButtons[0][i].getText().equals("X") ? "Joueur 1 (X)" : "Ordinateur (O)";
            }
        }

        if (!gridButtons[0][0].getText().equals("") &&
                gridButtons[0][0].getText().equals(gridButtons[1][1].getText()) &&
                gridButtons[0][0].getText().equals(gridButtons[2][2].getText())) {
            return gridButtons[0][0].getText().equals("X") ? "Joueur 1 (X)" : "Ordinateur (O)";
        }

        if (!gridButtons[0][2].getText().equals("") &&
                gridButtons[0][2].getText().equals(gridButtons[1][1].getText()) &&
                gridButtons[0][2].getText().equals(gridButtons[2][0].getText())) {
            return gridButtons[0][2].getText().equals("X") ? "Joueur 1 (X)" : "Ordinateur (O)";
        }

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
            writer.write("Mode de jeu : 1 Joueur");
            writer.newLine();
            writer.write("Date de la partie : " + currentDateTime);
            writer.newLine();
            writer.write("Score " + playerName + " : " + joueurScore + " | Score Ordinateur : " + ordiScore);
            writer.newLine();

            writer.write("======================================");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde de la partie.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Retourne au menu principal
     */
    private void returnToMenu() {
        // Sauvegarder les données de la partie avant de revenir au menu
        saveGameToFile();
        this.dispose();
        Morpion.createPlayMenu(true);
    }
}