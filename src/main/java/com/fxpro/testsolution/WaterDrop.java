package com.fxpro.testsolution;

import com.fxpro.testsolution.enums.Dirrection;
import com.fxpro.testsolution.enums.WaterDropFlowResult;
import com.fxpro.testsolution.enums.WaterDropMovingResult;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static com.fxpro.testsolution.enums.Dirrection.Down;
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
    private Point currentPoint = new Point();
    private Set<Point> track = new HashSet<>();
    private Dirrection currentDirrection = Down;

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
        landscape.print("===> WaterDrop.startFlow: " + currentPoint.i + ", " + currentPoint.j + "\n");
        while (true) {
            landscape.printTerrain();
            WaterDropMovingResult movingResult = moveDown();
            if (movingResult == Leak) return Missed;

            if (movingResult == CanNotMove) {
                movingResult = moveRight();
                if (movingResult == Leak) return Missed;
                if (movingResult == CanNotMove) {


                    movingResult = moveLeft(); // left
                    if (movingResult == Leak) return Missed; // left
                    if (movingResult == CanNotMove)  // left
                        return Stored;
                }
            }
            if (isLooped()) return Stored;
        }
    }

    private WaterDropMovingResult moveRight() {
        return move(currentPoint.i + 1, currentPoint.j);
    }

    private WaterDropMovingResult moveLeft() {
        return move(currentPoint.i - 1, currentPoint.j);
    }

    private WaterDropMovingResult moveDown() {
        return move(currentPoint.i, currentPoint.j - 1);
    }

    private WaterDropMovingResult move(int i, int j) {
        if (currentPoint.i - 1 < 0 ||
            currentPoint.i + 1 >= landscape.getLandscapeWidth() ||
            currentPoint.j - 1 < 0)
        {
            landscape.setTerrainType(currentPoint.i, currentPoint.j, Air);
            return Leak;
        }


        if (landscape.getTerrainType(i, j) != Air)
            return CanNotMove;
        else {
            landscape.changeCells(currentPoint.i, currentPoint.j, i, j);
            currentPoint.i = i;
            currentPoint.j = j;
            return Moved;
        }
    }

    private void putWaterDropOnTerrain(int i, int j) {
        landscape.setTerrainType(i, j, Water);
        currentPoint.i = i;
        currentPoint.j = j;
    }

    public boolean isLooped() {
        if (track.contains(currentPoint)) return true;
        else {
            track.add(currentPoint);
            return false;
        }
    }
}
