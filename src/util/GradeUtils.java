package util;

import java.util.Random;

import model.Player;
import model.item.AnimalProductGrade;

public class GradeUtils {
	private Random random;
	private Player player;

	public GradeUtils(Random random, Player player) {
		super();
		this.random = random;
		this.player = player;
	}

    public AnimalProductGrade getGrade() {
        int gradeRandomValue = random.nextInt(100) + 1;
        int day = player.getDay();

        int grade2Chance = day;
        int grade3Chance = day / 2;

        if (gradeRandomValue <= grade2Chance) {
            return AnimalProductGrade.GRADE_2;
        } else if (gradeRandomValue <= grade2Chance + grade3Chance) {
            return AnimalProductGrade.GRADE_3;
        } else {
            return AnimalProductGrade.GRADE_1;
        }
    }
}
