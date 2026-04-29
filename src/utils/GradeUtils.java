package utils;

import java.util.Random;

import models.Player;

public class GradeUtils {
	private Random rand;
	private Player player;

	public GradeUtils(Random rand, Player player) {
		super();
		this.rand = rand;
		this.player = player;
	}

	public int getGrade() {
        int gradeRand = rand.nextInt(100) + 1;
        int day = player.getDay();

        int grade2Chance = day;
        int grade3Chance = day / 2;

        if (gradeRand <= grade2Chance) {
            return 2;
        } else if (gradeRand <= grade2Chance + grade3Chance) {
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
