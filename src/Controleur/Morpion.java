package Controleur;

import Vue.Play.OnePlayer;
import Vue.Play.TwoPlayers;
import Vue.Play.PlayMenu;
import Vue.MorpionMenu;
import Vue.Settings.MorpionSettingsVueGenerale;

import javax.swing.*;


public class Morpion {

    private static MorpionMenu unMorpionMenu;
    private static MorpionSettingsVueGenerale unMorpionSettingsVueGenerale;
    private static PlayMenu unPlayMenu;
    private static TwoPlayers unTwoPlayers;
    private static OnePlayer unOnePlayer;

    public static void setVisibleMenu(boolean action){
        unMorpionMenu.setVisible(action);
    }

    public static void createMorpionSettingsVueGenerale(boolean action){
        if(action == true){
            unMorpionSettingsVueGenerale = new MorpionSettingsVueGenerale();
        }else{
            unMorpionSettingsVueGenerale.dispose();
        }
    }

    public static void createMorpionMenu(boolean action){
        if(action == true){
            unMorpionMenu = new MorpionMenu();
        }else{
            unMorpionMenu.dispose();
        }
    }

    public static void createPlayMenu(boolean action){
        if(action == true){
            unPlayMenu = new PlayMenu();
        }else{
            unPlayMenu.dispose();
        }
    }

    public static void setVisiblePlayMenu(boolean action){
        if(action == true){
            unPlayMenu.setVisible(true);
        }else {
            unPlayMenu.dispose();
        }
    }

    public static void createTwoPlayer(boolean action) {
        if (action) {
            // Demander les noms des joueurs
            String player1Name = askPlayerName("Joueur 1");
            String player2Name = askPlayerName("Joueur 2");

            // Instancier TwoPlayers avec les noms des joueurs
            unTwoPlayers = new TwoPlayers(player1Name, player2Name);
        } else {
            unTwoPlayers.dispose();
        }
    }

    /**
     * Crée une partie en mode 1 joueur en demandant le nom du joueur.
     */
    public static void createOnePlayer(boolean action) {
        if (action) {
            // Demander le nom du joueur
            String playerName = askPlayerName("Votre nom");

            // Instancier OnePlayer avec le nom du joueur
            unOnePlayer = new OnePlayer(playerName);
        } else {
            unOnePlayer.dispose();
        }
    }

    public static void setVisibleTwoPlayer(boolean action) {
        if (action) {
            unTwoPlayers.setVisible(true);
        } else {
            unTwoPlayers.dispose();
        }
    }


    /**
     * Affiche une boîte de dialogue pour demander le nom du joueur.
     * @param promptMessage Message affiché dans la boîte de dialogue
     * @return Nom saisi par l'utilisateur ou un nom par défaut s'il n'a rien entré
     */
    private static String askPlayerName(String promptMessage) {
        String playerName;
        do {
            playerName = JOptionPane.showInputDialog(null,
                    "Entrez le nom de " + promptMessage + " :",
                    "Nom du joueur",
                    JOptionPane.QUESTION_MESSAGE);
        } while (playerName != null && playerName.trim().isEmpty());

        return (playerName == null) ? promptMessage : playerName;
    }

    // Start Controleur.Morpion
    public static void main(String[] args) {
        unMorpionMenu =  new MorpionMenu();
    }
}



