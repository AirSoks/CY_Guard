package engine.utilitaire;

import org.apache.log4j.Logger;

import gui.panel.MainGUI;
import log.LoggerUtility;

public class ChronoSimulation {
	
	private static Logger logger = LoggerUtility.getLogger(ChronoSimulation.class, "html");
	
	private static ChronoSimulation instance;
	
    private long startTime = 0;
    private long totalTime = 0;
    private boolean running = false;

    public static ChronoSimulation getInstance() {
        if (instance == null) {
            instance = new ChronoSimulation();
        }
        return instance;
    }
    
    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
            logger.trace("Simulation en cours");
        }
        
    }

    public void pause() {
        if (running) {
            totalTime += System.currentTimeMillis() - startTime;
            running = false;
            logger.trace("Simulation en pause");
        }
        
    }

    public void reset() {
        totalTime = 0;
        startTime = 0;
        running = false;
    }

    public long getSimulationMiliseconds() {
        if (running) {
            return totalTime + (System.currentTimeMillis() - startTime);
        } else {
            return totalTime;
        }
    }

    public int getSimulationSecond() {
        return (int) (getSimulationMiliseconds() / 1000);
    }

    public boolean isRunning() {
        return running;
    }
}