package com.fxpro.testsolution;

import org.junit.Test;

import java.util.Arrays;

import static com.fxpro.testsolution.LandscapeModel.Terrain.Water;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import static com.fxpro.testsolution.LandscapeModel.Terrain.Mountain;
import static com.fxpro.testsolution.LandscapeModel.Terrain.Air;

public class LandscapeModelTest {

    private static final int[] model = new int[]{5, 2, 3, 4, 5, 4, 1, 3, 1};
    private static final LandscapeModel.Terrain[][] expectedLandscapeModelAfterGenerating = new LandscapeModel.Terrain[][]{
            {Mountain, Mountain, Mountain, Mountain, Mountain},
            {Mountain, Mountain, Air,      Air,      Air},
            {Mountain, Mountain, Mountain, Air,      Air},
            {Mountain, Mountain, Mountain, Mountain, Air},
            {Mountain, Mountain, Mountain, Mountain, Mountain},
            {Mountain, Mountain, Mountain, Mountain, Air},
            {Mountain, Air,      Air,      Air,      Air},
            {Mountain, Mountain, Mountain, Air,      Air},
            {Mountain, Air,      Air,      Air,      Air}
    };

    private static final LandscapeModel.Terrain[][] expectedLandscapeModelAfterRain = new LandscapeModel.Terrain[][]{
            {Mountain, Mountain, Mountain, Mountain, Mountain},
            {Mountain, Mountain, Water,    Water,    Water},
            {Mountain, Mountain, Mountain, Water,    Water},
            {Mountain, Mountain, Mountain, Mountain, Water},
            {Mountain, Mountain, Mountain, Mountain, Mountain},
            {Mountain, Mountain, Mountain, Mountain, Air},
            {Mountain, Water,    Water,    Air,      Air},
            {Mountain, Mountain, Mountain, Air,      Air},
            {Mountain, Air,      Air,      Air,      Air}
    };
    // *************************************************************************
    private static final int[] model2 = new int[]{4, 2, 1, 1, 6, 4, 0, 2, 4, 7, 4, 5};
    private static final LandscapeModel.Terrain[][] expectedLandscapeModelAfterGenerating2 = new LandscapeModel.Terrain[][]{
            {Mountain, Mountain, Mountain, Mountain, Air,      Air,      Air},
            {Mountain, Mountain, Air,      Air,      Air,      Air,      Air},
            {Mountain, Air,      Air,      Air,      Air,      Air,      Air},
            {Mountain, Air,      Air,      Air,      Air,      Air,      Air},
            {Mountain, Mountain, Mountain, Mountain, Mountain, Mountain, Air},
            {Mountain, Mountain, Mountain, Mountain, Air,      Air,      Air},
            {Air     , Air,      Air,      Air,      Air,      Air,      Air},
            {Mountain, Mountain, Air,      Air,      Air,      Air,      Air},
            {Mountain, Mountain, Mountain, Mountain, Air,      Air,      Air},
            {Mountain, Mountain, Mountain, Mountain, Mountain, Mountain, Mountain},
            {Mountain, Mountain, Mountain, Mountain, Air,      Air,      Air},
            {Mountain, Mountain, Mountain, Mountain, Mountain, Air,      Air}
    };

    private static final LandscapeModel.Terrain[][] expectedLandscapeModelAfterRain2 = new LandscapeModel.Terrain[][]{
            {Mountain, Mountain, Mountain, Mountain, Air,      Air,      Air},
            {Mountain, Mountain, Water,    Water,    Air,      Air,      Air},
            {Mountain, Water,    Water,    Water,    Air,      Air,      Air},
            {Mountain, Water,    Water,    Water,    Air,      Air,      Air},
            {Mountain, Mountain, Mountain, Mountain, Mountain, Mountain, Air},
            {Mountain, Mountain, Mountain, Mountain, Air,      Air,      Air},
            {Air     , Air,      Air,      Air,      Air,      Air,      Air},
            {Mountain, Mountain, Air,      Air,      Air,      Air,      Air},
            {Mountain, Mountain, Mountain, Mountain, Air,      Air,      Air},
            {Mountain, Mountain, Mountain, Mountain, Mountain, Mountain, Mountain},
            {Mountain, Mountain, Mountain, Mountain, Water,    Air,      Air},
            {Mountain, Mountain, Mountain, Mountain, Mountain, Air,      Air}
    };


    @Test
    public void landscapeModelTest1() {
        LandscapeModel landscapeModel = new LandscapeModel(model);

        assertTrue(Arrays.deepEquals(expectedLandscapeModelAfterGenerating, landscapeModel.getModel()));
        assertEquals(0, landscapeModel.getWaterVolume());

        landscapeModel.startRain();
        assertTrue(Arrays.deepEquals(expectedLandscapeModelAfterRain, landscapeModel.getModel()));

        assertEquals(8, landscapeModel.getWaterVolume());
    }

    @Test
    public void landscapeModelTest2() {
        LandscapeModel landscapeModel = new LandscapeModel(model2);

        assertTrue(Arrays.deepEquals(expectedLandscapeModelAfterGenerating2, landscapeModel.getModel()));
        assertEquals(0, landscapeModel.getWaterVolume());

        landscapeModel.startRain();
        assertTrue(Arrays.deepEquals(expectedLandscapeModelAfterRain2, landscapeModel.getModel()));

        assertEquals(9, landscapeModel.getWaterVolume());
    }

    @Test
    public void flatLandscapeModelTest() {
        LandscapeModel landscapeModel = new LandscapeModel(new int[]{3, 3, 3, 3, 3});
        assertEquals(0, landscapeModel.getWaterVolume());

        landscapeModel.startRain();
        assertEquals(0, landscapeModel.getWaterVolume());
    }
}
