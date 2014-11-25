package services;
import java.util.Random;
public class MyRandom extends Random {
    public MyRandom() {}
    public MyRandom(long seed) { super(seed); }

    public int nextNonNegative() {
        return next(31);
    }
}