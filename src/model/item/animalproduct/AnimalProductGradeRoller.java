package model.item.animalproduct;

import java.util.Random;


public class AnimalProductGradeRoller {
	private final Random random;

	public AnimalProductGradeRoller(Random random) {
		this.random = random;
	}

	public AnimalProductGrade getGrade(int day) {
		int roll = random.nextInt(100) + 1;
		int grade3Chance = day / 2;
		int grade2Chance = day;

		if (roll <= grade3Chance) {
			return AnimalProductGrade.GRADE_3;
		} else if (roll <= grade3Chance + grade2Chance) {
			return AnimalProductGrade.GRADE_2;
		} else {
			return AnimalProductGrade.GRADE_1;
		}
	}
}
