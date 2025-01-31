package Controleur;

import Vue.Play.OnePlayer;
import Vue.Play.TwoPlayers;
import Vue.Play.PlayMenu;
import Vue.MorpionMenu;
import Vue.Settings.MorpionSettingsVueGenerale;


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

    public static void createTwoPlayer(boolean action){
        if(action == true){
            unTwoPlayers = new TwoPlayers();
        }else {
            unTwoPlayers.dispose();
        }
    }

    public static void createOnePlayer(boolean action){
        if(action == true){
            unOnePlayer = new OnePlayer();
        }else {
            unOnePlayer.dispose();
        }
    }

    public static void setVisibleTwoPlayer(boolean action){
        if(action == true){
            unTwoPlayers = new TwoPlayers();
        }else{
            unTwoPlayers.dispose();
        }
    }

    // Start Controleur.Morpion
    public static void main(String[] args) {
        unMorpionMenu =  new MorpionMenu();

    }


}



