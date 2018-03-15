package com.fxpro.testsolution;

public class TestData {
    private int[] model;
    private int expectedVolume;

    public TestData(int[] model, int expectedVolume) {
        this.model = model;
        this.expectedVolume = expectedVolume;
    }

    public int[] getModel() {
        return model;
    }

    public int getExpectedVolume() {
        return expectedVolume;
    }
}