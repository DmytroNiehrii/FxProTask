package com.fxpro.testsolution;

import com.fxpro.testsolution.enums.WaterDropFlowResult;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static com.fxpro.testsolution.enums.WaterDropFlowResult.Missed;
import static com.fxpro.testsolution.enums.WaterDropFlowResult.Stored;

@Data
public class WaterSource implements Supplier<Integer> {
    private Rain rain;
    private int terrainPosition;
    private Set<CompletableFuture<WaterDropFlowResult>> dropFlowResults = new HashSet<>();
    private Integer storedDropsCount = 0;
    private boolean isDone = false;

    // How many water drops left on terrain
    @Override
    public Integer get() {
        while (!isDone) {
            CompletableFuture<WaterDropFlowResult> future = CompletableFuture.supplyAsync(new WaterDrop(rain.getLandscape(), terrainPosition));
            future.thenAccept(result -> {
                if (result == Missed) isDone = true;
                if (result == Stored) storedDropsCount++;
            });
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return storedDropsCount;
    }
}
