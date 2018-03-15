package com.fxpro.testsolution;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class LandscapeTest {


    private static final TestData[] testData = new TestData[]{
            new TestData(new int[]{5, 2, 3, 4, 5, 4, 1, 3, 1}, 8),
            new TestData(new int[]{4, 2, 1, 1, 6, 4, 0, 2, 4, 7, 4, 5}, 9),
    };

    @Test
    public void testModel_0() {
        assertEquals(testData[0].getExpectedVolume(), Landscape.getWaterVolume(testData[0].getModel()));
    }

    @Test
    public void testModel_1() {
        assertEquals(testData[1].getExpectedVolume(), Landscape.getWaterVolume(testData[1].getModel()));
    }
}