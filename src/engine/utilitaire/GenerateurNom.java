package engine.utilitaire;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Cette classe représente la génération de nom aléatoire 
 * 
 * @author GLP_19
 * @see Personnage
 */
public class GenerateurNom {
    private static final List<String> PRENOMS = Arrays.asList(
        "Emma", "Liam", "Olivia", "Noah", "Eva", "Julie", "Sophia", "Chloé",
        "Lisa", "Alexis", "Mia", "James", "Léo", "Joe", "Maria"
    );

    private static final List<String> NOMS = Arrays.asList(
        "Smith", "Rey", "Brown", "Taylor", "Miller", "Wilson", "Martin", "Jean",
        "Petit", "Gillet", "Duval", "Harris", "Martin", "Morel", "Garcia"
    );

    private static final Random RANDOM = new Random();

    public static String genererNom() {
        String prenom = PRENOMS.get(RANDOM.nextInt(PRENOMS.size()));
        String nom = NOMS.get(RANDOM.nextInt(NOMS.size()));
        return prenom + " " + nom;
    }
}