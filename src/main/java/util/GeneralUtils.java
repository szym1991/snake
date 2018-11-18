package util;

import lombok.experimental.UtilityClass;
import position.Position;

import java.util.Random;

@UtilityClass
public class GeneralUtils {

    public static int randomValue(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    public static double randomValue() {
        return new Random().nextDouble();
    }

    public double calculateDistance(Position p1, Position p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2)
                + Math.pow(p1.getY() - p2.getY(), 2));
    }
}
