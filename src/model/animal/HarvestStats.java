package model.animal;

public class HarvestStats {
    private int currentRate;
    private final int defaultRate;
    private boolean harvestable;

    public HarvestStats(int currentRate, int defaultRate, boolean harvestable) {
        this.currentRate = currentRate;
        this.defaultRate = defaultRate;
        this.harvestable = harvestable;
    }

    public int getCurrentRate() {
        return currentRate;
    }

    public int getDefaultRate() {
        return defaultRate;
    }

    public boolean isHarvestable() {
        return harvestable;
    }

    public void tick() {
        if (harvestable) return;
        currentRate--;
        if (currentRate == 0) {
            harvestable = true;
            currentRate = defaultRate;
        }
    }

    public void collect() {
        harvestable = false;
        currentRate = defaultRate;
    }
}
