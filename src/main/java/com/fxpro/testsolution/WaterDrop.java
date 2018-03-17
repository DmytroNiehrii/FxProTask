package com.fxpro.testsolution;

import com.fxpro.testsolution.enums.Dirrection;
import com.fxpro.testsolution.enums.TerrainType;
import com.fxpro.testsolution.enums.WaterDropFlowResult;
import com.fxpro.testsolution.enums.WaterDropMovingResult;

import java.util.function.Supplier;

import static com.fxpro.testsolution.enums.Dirrection.Left;
import static com.fxpro.testsolution.enums.Dirrection.Right;
import static com.fxpro.testsolution.enums.TerrainType.Air;
import static com.fxpro.testsolution.enums.WaterDropFlowResult.Conflict;
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
    private Dirrection currentDirrection = Right;
    private boolean isMovingDown = false;
    private int switchingDirrectionCount = 0;

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
            return result;
        } else return Missed;
    }

    private WaterDropFlowResult startFlow() {
        while (true) {
            printTerrain();
            WaterDropMovingResult movingResult = moveDown();
            if (movingResult == Leak) return Missed;

            if (movingResult == CanNotMove) {
                movingResult = moveToDirrection();
                if (movingResult == Leak) return Missed;
                if (movingResult == CanNotMove) {

                    movingResult = moveToDirrection();
                    if (movingResult == Leak) return Missed;
                    if (movingResult == CanNotMove)
                        return done();
                }
            }
            if (isLooped()) return done();
        }
    }

    public synchronized void printTerrain() {
        print("================= Terrain ================\n");
        for (int j = landscape.getLandscapeHight(0)-1; j >= 0; j--) {
            for (int i = 0; i < landscape.getLandscapeWidth(); i++ ) {

                if (currentPoint.i == i && currentPoint.j == j)
                    print("+");
                else
                    print(getTerrainTypeView(landscape.getTerrainType(i, j)));
            }
            print("\n");
        }
        print("\n\n");
    }

    private String getTerrainTypeView(TerrainType terrainType) {
        switch (terrainType) {
            case Mountain: return "X";
            case Air: return " ";
            case Water: return "~";
        }
        return "";
    }

    public synchronized void print(String s) {
        System.out.print(s);
    }

    private WaterDropFlowResult done() {
        if (landscape.putWater(currentPoint))
            return Stored;
        else
            return Conflict;
    }

    private WaterDropMovingResult moveDown() {
        isMovingDown = true;
        return move(currentPoint.down());
    }

    private WaterDropMovingResult moveToDirrection() {
        isMovingDown = false;
        switch (currentDirrection) {
            case Left: return move(currentPoint.left());
            //move Right
            default: return move(currentPoint.right());
        }
    }

    private WaterDropMovingResult move(Point destinationPoint) {
        if (currentPoint.i - 1 < 0 ||
            currentPoint.i + 1 >= landscape.getLandscapeWidth() ||
            currentPoint.j - 1 < 0)
        {
            landscape.setTerrainType(currentPoint.i, currentPoint.j, Air);
            return Leak;
        }

        if (landscape.getTerrainType(destinationPoint.i, destinationPoint.j) != Air) {
            switchDirrection();
            return CanNotMove;
        } else {
            currentPoint.i = destinationPoint.i;
            currentPoint.j = destinationPoint.j;
            return Moved;
        }
    }

    private void switchDirrection() {
        if (isMovingDown) return;
        switchingDirrectionCount++;
        switch (currentDirrection) {
            case Right: currentDirrection = Left; break;
            case Left: currentDirrection = Right; break;
        }
    }

    private void putWaterDropOnTerrain(int i, int j) {
        currentPoint.i = i;
        currentPoint.j = j;
    }

    public boolean isLooped() {
        return switchingDirrectionCount > 1;
    }
}
