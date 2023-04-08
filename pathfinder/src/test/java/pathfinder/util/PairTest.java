package pathfinder.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PairTest {


    @Test
    public void pairTest() {
        Integer first = 15;
        String second = "Test";

        Pair<Integer, String> pair = new Pair<>(first, second);

        assertEquals(first, pair.first());
        assertEquals(second, pair.second());
    }

}
