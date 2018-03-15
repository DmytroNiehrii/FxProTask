package com.fxpro.testsolution;

import java.util.Arrays;
import java.util.List;

import static com.fxpro.testsolution.LandscapeModel.Terrain.Air;
import static com.fxpro.testsolution.LandscapeModel.Terrain.Water;

import static com.fxpro.testsolution.LandscapeModel.Dirrection.Down;
import static com.fxpro.testsolution.LandscapeModel.Dirrection.Left;
import static com.fxpro.testsolution.LandscapeModel.Dirrection.Right;

public class LandscapeModel {

    private static final List<Dirrection> ALL_DIRRECTIONS = Arrays.asList(Down, Left, Right);
    private static final List<Dirrection> NO_LEFT_DIRRECTION = Arrays.asList(Down, Right);
    private static final List<Dirrection> NO_RIGHT_DIRRECTION = Arrays.asList(Down, Left);

    public enum Terrain {Mountain, Air, Water};
    public enum Dirrection {Down, Left, Right};
    public enum MoveWaterDropResult {Leak, Done, Fail, Full};

    private Terrain[][] landscapeModel;
    private int waterVolume = 0;

    public LandscapeModel(int[] model) {
        generateLandscapeModel(model);
    }

    public void startRain() {
        for (int i = 0; i < landscapeModel.length; i++)
            startRainOverOneCell(i);
        calculateWaterVolumeAndRemoveWaves();
    }

    private void calculateWaterVolumeAndRemoveWaves() {
        waterVolume = 0;
        for (int i = 0; i < landscapeModel.length; i++)
            for (int j = 0; j <= landscapeModel[i].length - 1; j++) {
                if (landscapeModel[i][j] == Water && i <  landscapeModel.length - 1 && landscapeModel[i+1][j] == Air)
                    landscapeModel[i][j] = Air;
                if (landscapeModel[i][j] == Water) waterVolume++;
            }
    }

    private void startRainOverOneCell(int i) {
        int j = landscapeModel[i].length - 1;
        MoveWaterDropResult status = MoveWaterDropResult.Done;
        while (landscapeModel[i][j] == Air && status != MoveWaterDropResult.Leak && status != MoveWaterDropResult.Full) {
            status = runWaterDrop(i, j);
        }
    }

    private MoveWaterDropResult runWaterDrop(int i, int j) {
        if (!putWaterDrop(i, j)) return MoveWaterDropResult.Full;
        return moveWaterDrop(i, j, ALL_DIRRECTIONS);
    }


    private MoveWaterDropResult moveWaterDrop(int i, int j, List<Dirrection> allowDirrections) {
        if (allowDirrections.indexOf(Down) != -1) {
            MoveWaterDropResult result = moveWaterDrop(i, j, Down);
            if (result != MoveWaterDropResult.Fail) return result;
        }

        if (allowDirrections.indexOf(Left) != -1) {
            MoveWaterDropResult result = moveWaterDrop(i, j, Left);
            if (result != MoveWaterDropResult.Fail) return result;
        }

        if (allowDirrections.indexOf(Right) != -1) {
            MoveWaterDropResult result = moveWaterDrop(i, j, Right);
            if (result != MoveWaterDropResult.Fail) return result;
        }

        return MoveWaterDropResult.Done;
    }

    private MoveWaterDropResult moveWaterDrop(int i, int j, Dirrection dirrection) {
        int I = i, J = j;
        switch (dirrection) {
            case Down:  J--; break;
            case Left:  I--; break;
            case Right: I++; break;
        }

        // Check leak
        if (I < 0 || I >= landscapeModel.length || J < 0){
            landscapeModel[i][j] = Air;
            return MoveWaterDropResult.Leak;
        }

        if (landscapeModel[i][j] == Water && landscapeModel[I][J] == Air) {
            landscapeModel[i][j] = Air;
            landscapeModel[I][J] = Water;
            switch (dirrection) {
                case Down: return moveWaterDrop(I, J, ALL_DIRRECTIONS);
                case Left: return moveWaterDrop(I, J, NO_RIGHT_DIRRECTION);
                case Right: return moveWaterDrop(I, J, NO_LEFT_DIRRECTION);
            }
            return MoveWaterDropResult.Done;
        } else
            if (dirrection == Dirrection.Right) return MoveWaterDropResult.Done;
            else return MoveWaterDropResult.Fail;
    }

    private boolean putWaterDrop(int i, int j) {
        if (landscapeModel[i][j] == Air) {
            landscapeModel[i][j] = Water;
            return true;
        } else return false;
    }

    public Terrain[][] getModel() {
        return landscapeModel;
    }

    private void generateLandscapeModel(int[] model) {
        int mountainMaxSize = Utils.getArrayMaxValue(model);
        landscapeModel = new Terrain[model.length][mountainMaxSize];

        // Fill all landscape model by Air
        for (int i = 0; i < model.length; i++)
            for (int j = 0; j < mountainMaxSize; j++)
                landscapeModel[i][j] = Terrain.Air;

        // Fill landscape model by Mountain according to model
        for (int i = 0; i < model.length; i++)
            for (int j = 0; j < model[i]; j++)
                landscapeModel[i][j] = Terrain.Mountain;

    }

    public int getWaterVolume() {
        return this.waterVolume;
    }
}
