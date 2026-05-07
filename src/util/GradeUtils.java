package util;

import java.util.Random;

import models.Player;

public class GradeUtils {
	private Random random;
	private Player player;

	public GradeUtils(Random random, Player player) {
		super();
		this.random = random;
		this.player = player;
	}

	public int getGrade() {
        int gradeRandomValue = random.nextInt(100) + 1;
        int day = player.getDay();

        int grade2Chance = day;
        int grade3Chance = day / 2;

        if (gradeRandomValue <= grade2Chance) {
            return 2;
        } else if (gradeRandomValue <= grade2Chance + grade3Chance) {
            return 3;
        } else {
            return 1;
        }
    }
	
	public int getGradeMultiplier (int grade) {
		if (grade == 1) 
			return 1;
		else if (grade == 2) 
			return 2;
		else 
			return 5;
	}
}
