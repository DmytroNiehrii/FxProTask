package com.fxpro.testsolution;

import com.fxpro.testsolution.enums.Terrain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.fxpro.testsolution.enums.Terrain.Air;
import static com.fxpro.testsolution.enums.Terrain.Mountain;
import static com.fxpro.testsolution.enums.Terrain.Water;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    private static final int[] model = new int[]{5, 2, 3, 4, 5, 4, 1, 3, 1};
    private static final Terrain[][] expectedLandscapeModelAfterGenerating = new Terrain[][]{
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

    private static final Terrain[][] expectedLandscapeModelAfterRain = new Terrain[][]{
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
    private static final Terrain[][] expectedLandscapeModelAfterGenerating2 = new Terrain[][]{
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

    private static final Terrain[][] expectedLandscapeModelAfterRain2 = new Terrain[][]{
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

    @Autowired
    private Landscape landscape;

    @Test
    public void landscapeModelTest1() {
        landscape.generateLandscapeModel(model);

        assertTrue(Arrays.deepEquals(expectedLandscapeModelAfterGenerating, landscape.getTerrain()));
        assertEquals(5, landscape.startRain());
        //assertEquals(0, landscapeModel.getWaterVolume());

        //landscapeModel.startRain();
        //assertTrue(Arrays.deepEquals(expectedLandscapeModelAfterRain, landscapeModel.getTerrain()));

        //assertEquals(8, landscapeModel.getWaterVolume());
    }

    @Test
    public void landscapeModelTest2() {
        landscape.generateLandscapeModel(model2);

        assertTrue(Arrays.deepEquals(expectedLandscapeModelAfterGenerating2, landscape.getTerrain()));
        //assertEquals(0, landscapeModel.getWaterVolume());

        //landscapeModel.startRain();
        //assertTrue(Arrays.deepEquals(expectedLandscapeModelAfterRain2, landscapeModel.getTerrain()));

        //assertEquals(9, landscapeModel.getWaterVolume());
    }

}
