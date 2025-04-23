package engine.utilitaire.chrono;
public class ChronoSimulation {
	
    private long startTime = 0;
    private long totalTime = 0;
    private boolean running = false;

    private ChronoListener listener;
    private int lastSecondNotified = -1;

    public void setListener(ChronoListener listener) {
        this.listener = listener;
    }

    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void pause() {
        if (running) {
            totalTime += System.currentTimeMillis() - startTime;
            running = false;
        }
    }

    public void reset() {
        totalTime = 0;
        startTime = 0;
        lastSecondNotified = -1;
        running = false;
        tick();
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

    public void tick() {
        int currentSecond = getSimulationSecond();
        if (listener != null && currentSecond != lastSecondNotified) {
            lastSecondNotified = currentSecond;
            listener.onNewSecond(currentSecond);
        }
    }

    public boolean isRunning() {
        return running;
    }
}