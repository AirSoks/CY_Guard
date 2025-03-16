package engine.personnage.deplacement;

import java.util.HashMap;
import java.util.Map;

import engine.map.Grille;
import engine.personnage.PersonnageManager;

public class DeplacementFactory {
    private static final Map<String, Deplacement> deplacements = new HashMap<>();

    public static Deplacement getDeplacement(String type, PersonnageManager personnages, Grille grille) {
        Deplacement deplacement = deplacements.get(type);
        
        if (deplacement == null) {
            switch (type) {
                case "Intelligent":
                    deplacement = new DeplacementIntelligent(personnages, grille);
                    break;
                case "Aleatoire":
                    deplacement = new DeplacementAleatoire(personnages, grille);
                    break;
                case "Manuel":
                    deplacement = new DeplacementManuel(personnages, grille);
                    break;
                default:
                	deplacement = new DeplacementAleatoire(personnages, grille);
            }
            deplacements.put(type, deplacement);
        }
        return deplacement;
    }
}
