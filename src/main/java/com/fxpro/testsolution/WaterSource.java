package com.fxpro.testsolution;

import com.fxpro.testsolution.enums.WaterDropFlowResult;
import lombok.Data;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Data
public class WaterSource implements Supplier<Integer> {
    private Rain rain;
    private int terrainPosition;

    // How many water drops left on terrain
    @Override
    public Integer get() {
        CompletableFuture<WaterDropFlowResult> feature = putNewWaterDrop();


        //ToDo CompletableFuture.supplyAsync()
        return 3;
    }

    private CompletableFuture<WaterDropFlowResult> putNewWaterDrop() {
        //ToDo WaterDrop waterDrop = new Wa
        return null;
    }
}
