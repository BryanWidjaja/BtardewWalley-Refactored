package model.plants;

public class GrowthDuration {
    private int daysRemaining;

    public GrowthDuration(int daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    public int getDaysRemaining() {
        return daysRemaining;
    }

    public boolean tick() {
        if (daysRemaining <= 0) {
            return false;
        }
        daysRemaining--;
        return daysRemaining == 0;
    }

    public boolean isComplete() {
        return daysRemaining <= 0;
    }
}
