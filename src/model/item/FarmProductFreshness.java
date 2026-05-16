package model.item;

public enum FarmProductFreshness {
    LEVEL_5(5, 1.0),
    LEVEL_4(4, 1.0),
    LEVEL_3(3, 0.5),
    LEVEL_2(2, 0.5),
    LEVEL_1(1, 0.25),
    EXPIRED(0, 0.0);

    private final int level;
    private final double multiplier;

    FarmProductFreshness(int level, double multiplier) {
        this.level = level;
        this.multiplier = multiplier;
    }

    public int getLevel() {
        return level;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public boolean isExpired() {
        return this == EXPIRED;
    }

    public FarmProductFreshness getNextState() {
        if (this == EXPIRED) {
            return EXPIRED;
        }
        return fromLevel(level - 1);
    }

    public static FarmProductFreshness fromLevel(int level) {
        for (FarmProductFreshness freshness : values()) {
            if (freshness.level == level) {
                return freshness;
            }
        }
        return EXPIRED;
    }
}
