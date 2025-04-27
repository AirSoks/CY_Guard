package engine.utilitaire;

import org.apache.log4j.Logger;

import log.LoggerUtility;

/**
 * Classe gérant le chronomètre de la simulation.
 * 
 * Elle permet de démarrer, mettre en pause, réinitialiser le temps
 * et de récupérer la durée écoulée en millisecondes ou en secondes.
 * 
 * @author GLP_19
 * @see MainGUI
 */
public class ChronoSimulation {
	
	/**
     * Les logs de la simulation
     */
	private static Logger logger = LoggerUtility.getLogger(ChronoSimulation.class, "html");
	
	/**
	 * Utilisation d'un singleton
	 */
	private static ChronoSimulation instance;
	
	/**
	 * Temps de démarrage de la simulation en millisecondes.
	 */
    private long startTime = 0;
    
    /**
	 * Temps total accumulé de la simulation en millisecondes.
	 */
    private long totalTime = 0;
    
    /**
	 * Indique si le chronomètre est actuellement en cours d'exécution.
	 */
    private boolean running = false;

    public static ChronoSimulation getInstance() {
        if (instance == null) {
            instance = new ChronoSimulation();
        }
        return instance;
    }
    
    /**
     * Démarre ou reprend le chronomètre.
     */
    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
            logger.trace("Simulation en cours");
        }
    }

    /**
     * Met en pause le chronomètre en accumulant le temps écoulé.
     */
    public void pause() {
        if (running) {
            totalTime += System.currentTimeMillis() - startTime;
            running = false;
            logger.trace("Simulation en pause");
        }
        
    }

    /**
     * Réinitialise le chronomètre.
     */
    public void reset() {
        totalTime = 0;
        startTime = 0;
        running = false;
    }

    /**
     * Renvoie le temps total écoulé de la simulation en millisecondes.
     *
     * @return Temps écoulé en millisecondes.
     */
    public long getSimulationMiliseconds() {
        if (running) {
            return totalTime + (System.currentTimeMillis() - startTime);
        } else {
            return totalTime;
        }
    }

    /**
     * Renvoie le temps total écoulé de la simulation en secondes.
     *
     * @return Temps écoulé en secondes.
     */
    public int getSimulationSecond() {
        return (int) (getSimulationMiliseconds() / 1000);
    }

    public boolean isRunning() {
        return running;
    }
}