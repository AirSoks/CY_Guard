package engine.personnage.deplacement;

import java.util.HashMap;
import java.util.Map;

import engine.map.Grille;
import engine.personnage.PersonnageManager;

/**
 * Cette classe gère les différents types de déplacements pour les personnages.
 * Factory permettant de créer et de gérer les instances de stratégies de déplacement.
 *
 * @author GLP_19
 * @see DeplacementPoursuite
 * @see DeplacementAleatoire
 * @see DeplacementManuel
 */
public class DeplacementFactory {
	
	/**
     * Les instances de déplacement existantes (Singleton)
     */
    private static Map<String, Deplacement> deplacements = new HashMap<>();

    /**
     * Récupère une instance de stratégie de déplacement selon le type spécifié.
     * Crée une nouvelle instance si elle n'existe pas déjà.
     *
     * @param type Type de déplacement ("Intelligent", "Aleatoire" ou "Manuel")
     * @param personnages Gestionnaire des personnages du jeu
     * @param grille Grille de jeu utilisée pour les déplacements
     */
    public static Deplacement getDeplacement(String type, PersonnageManager personnages, Grille grille) {
    	
        if ("Poursuite".equals(type)) {
            return new DeplacementPoursuite(personnages, grille);
        }
        if ("Case".equals(type)) {
            return new DeplacementCase(personnages, grille);
        }

        Deplacement deplacement = deplacements.get(type);
        if (deplacement == null) {
            switch (type) {
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