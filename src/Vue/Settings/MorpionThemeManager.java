package Vue.Settings;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class MorpionThemeManager {
    private static Color backgroundColor = Color.CYAN; // Couleur initiale
    private static List<ColorChangeListener> listeners = new ArrayList<>();

    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    public static void setBackgroundColor(Color newColor) {
        backgroundColor = newColor;
        notifyListeners();
    }

    public static void addColorChangeListener(ColorChangeListener listener) {
        listeners.add(listener);
    }

    private static void notifyListeners() {
        for (ColorChangeListener listener : listeners) {
            listener.onColorChange(backgroundColor);
        }
    }

    // Interface pour informer les composants du changement de couleur
    public interface ColorChangeListener {
        void onColorChange(Color newColor);
    }
}