package model.item;

public enum AnimalProductGrade {
    GRADE_1(1, 1),
    GRADE_2(2, 2),
    GRADE_3(3, 5);

    private final int level;
    private final int multiplier;

    AnimalProductGrade(int level, int multiplier) {
        this.level = level;
        this.multiplier = multiplier;
    }

    public int getLevel() {
        return level;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public static AnimalProductGrade fromLevel(int level) {
        for (AnimalProductGrade g : values()) {
            if (g.level == level) return g;
        }
        return GRADE_1;
    }
}
