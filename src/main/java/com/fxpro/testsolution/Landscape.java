package com.fxpro.testsolution;

public class Landscape {

    public static int getWaterVolume(int[] model) {
        LandscapeModel landscapeModel = new LandscapeModel(model);
        landscapeModel.startRain();

        return landscapeModel.getWaterVolume();
    }





}
