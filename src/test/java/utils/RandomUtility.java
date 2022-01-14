package utils;

import java.util.Random;

public class RandomUtility {

    public static int randomValue(int from, int to) {
        return (int) (from + new Random().nextFloat() * (to - from));
    }

}
