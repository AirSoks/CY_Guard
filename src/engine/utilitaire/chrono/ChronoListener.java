package engine.utilitaire.chrono;

/**
 * Interface représentant un écouteur pour un ChronoSimulation.
 * 
 * Cette interface permet d'être notifié à chaque fois qu'une nouvelle seconde
 * est atteinte dans la simulation (hors temps de pause).
 */
public interface ChronoListener {

    /**
     * Méthode appelée automatiquement à chaque fois qu'une nouvelle seconde
     * complète est atteinte par le ChronoSimulation.
     * 
     * @param seconds Le temps écoulé en secondes depuis le début de la simulation.
     */
    void onNewSecond(int seconds);

}