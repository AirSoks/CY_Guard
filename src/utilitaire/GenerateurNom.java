package utilitaire;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GenerateurNom {
    private static final List<String> PRENOMS = Arrays.asList(
        "Emma", "Liam", "Olivia", "Noah", "Ava", "Quentin", "Sophia", "Redouane",
        "Isabella", "Valentin", "Mia", "James", "Charlotte", "Joe", "Amelia"
    );

    private static final List<String> NOMS = Arrays.asList(
        "Smith", "Johnson", "Brown", "Taylor", "Miller", "Wilson", "Rodriguez", "Anderson",
        "Thomas", "Jackson", "Roberts", "Harris", "Martin", "Thompson", "Garcia"
    );

    private static final Random RANDOM = new Random();

    public static String genererNom() {
        String prenom = PRENOMS.get(RANDOM.nextInt(PRENOMS.size()));
        String nom = NOMS.get(RANDOM.nextInt(NOMS.size()));
        return prenom + " " + nom;
    }
}