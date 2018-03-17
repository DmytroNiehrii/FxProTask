package com.fxpro.testsolution;

import com.fxpro.testsolution.enums.WaterDropFlowResult;
import com.fxpro.testsolution.enums.WaterDropMovingResult;

import java.util.function.Supplier;

import static com.fxpro.testsolution.enums.TerrainType.Air;
import static com.fxpro.testsolution.enums.TerrainType.Water;
import static com.fxpro.testsolution.enums.WaterDropFlowResult.Missed;
import static com.fxpro.testsolution.enums.WaterDropFlowResult.Stored;
import static com.fxpro.testsolution.enums.WaterDropMovingResult.CanNotMove;
import static com.fxpro.testsolution.enums.WaterDropMovingResult.Leak;
import static com.fxpro.testsolution.enums.WaterDropMovingResult.Moved;

public class WaterDrop implements Supplier<WaterDropFlowResult> {
    private final int terrainPosition;
    private final int topIndex;
    private final Landscape landscape;
    private int currentI, currentJ;

    public WaterDrop(Landscape landscape, int terrainPosition) {
        this.landscape = landscape;
        this.terrainPosition = terrainPosition;
        this.topIndex = landscape.getLandscapeHight(terrainPosition) - 1;
    }

    @Override
    public WaterDropFlowResult get() {
        if (landscape.getTerrainType(terrainPosition, topIndex) == Air) {
            putWaterDropOnTerrain(terrainPosition, topIndex);
            WaterDropFlowResult result = startFlow();
            landscape.print("<=== WaterDrop.doneFlow, result: " + result + "\n");
            landscape.printTerrain();
            return result;
        } else return Missed;
    }

    private WaterDropFlowResult startFlow() {
        landscape.printTerrain();
        landscape.print("===> WaterDrop.startFlow: " + currentI + ", " + currentJ + "\n");
        while (true) {
            landscape.printTerrain();
            WaterDropMovingResult movingResult = moveDown();
            if (movingResult == Leak) return Missed;

            if (movingResult == CanNotMove) {
                movingResult = moveRight();
                if (movingResult == Leak) return Missed;
                if (movingResult == CanNotMove) {
                    return Stored;
                }
            }
        }
    }

    private WaterDropMovingResult moveRight() {
        return move(currentI + 1, currentJ);
    }

    private WaterDropMovingResult moveLeft() {
        return move(currentI - 1, currentJ);
    }

    private WaterDropMovingResult moveDown() {
        return move(currentI, currentJ - 1);
    }

    private WaterDropMovingResult move(int i, int j) {
        if (currentI - 1 < 0 ||
            currentI + 1 >= landscape.getLandscapeWidth() ||
            currentJ - 1 < 0)
        {
            landscape.setTerrainType(currentI, currentJ, Air);
            return Leak;
        }


        if (landscape.getTerrainType(i, j) != Air)
            return CanNotMove;
        else {
            landscape.changeCells(currentI, currentJ, i, j);
            currentI = i;
            currentJ = j;
            return Moved;
        }
    }

    private void putWaterDropOnTerrain(int i, int j) {
        landscape.setTerrainType(i, j, Water);
        currentI = i;
        currentJ = j;
    }

    private Landscape getLandscaзe() {
        return landscape;
    }
}
