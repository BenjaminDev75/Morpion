package Controleur;

import Vue.MorpionMenu;
import Vue.Settings.MorpionSettingsVueGenerale;


public class Morpion {

    private static MorpionMenu unMorpionMenu;
    private static MorpionSettingsVueGenerale unMorpionSettingsVueGenerale;

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


    // Start Controleur.Morpion
    public static void main(String[] args) {
        unMorpionMenu =  new MorpionMenu();

    }


}



