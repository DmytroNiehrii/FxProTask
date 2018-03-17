package com.fxpro.testsolution;

import com.fxpro.testsolution.enums.WaterDropFlowResult;
import lombok.Data;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static com.fxpro.testsolution.enums.WaterDropFlowResult.Stored;

@Data
public class WaterSource implements Supplier<Integer> {
    private Rain rain;
    private int terrainPosition;
    private BlockingQueue<CompletableFuture<WaterDropFlowResult>> dropFlowResults = new LinkedBlockingQueue<>(1);
    private int storedDropsCount;
    private boolean isWaterSourceStopped = false;
    private Thread waterDropProducer;
    private Thread waterDropReceiver;

    // How many water drops left on terrain
    @Override
    public Integer get() {
        // Start water drops producer
        waterDropProducer = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    dropFlowResults.put(CompletableFuture.supplyAsync(new WaterDrop(rain.getLandscape(), terrainPosition)));
                    //Thread.sleep(2000);
                } catch (/*Interrupted*/Exception e) {
                    //e.printStackTrace();
                    System.out.println("waterDropProducer stopped");
                    return;
                }
            }
        });
        waterDropProducer.start();

        // Start water drops receiver
        waterDropReceiver = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    CompletableFuture<WaterDropFlowResult> feature = dropFlowResults.take();
                    WaterDropFlowResult dropFlowResult = feature.get();
                    if (dropFlowResult == Stored) {
                        this.storedDropsCount++;
                    } else {
                        this.waterDropLeakHandler();
                        System.out.println("waterDropReceiver stopped");
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("waterDropReceiver stopped");
                    return;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    System.out.println("waterDropReceiver stopped");
                    return;
                }
            }
        });
        waterDropReceiver.start();

        while(!isWaterSourceStopped);
        return this.storedDropsCount;
    }

    private void waterDropLeakHandler() {
        isWaterSourceStopped = true;
        waterDropProducer.stop();// interrupt();
        waterDropReceiver.interrupt();
    }

    // Not needed
    private void putNewWaterDrop() {
        WaterDrop waterDrop = new WaterDrop(rain.getLandscape(), terrainPosition);
        dropFlowResults.add(CompletableFuture.supplyAsync(new WaterDrop(rain.getLandscape(), terrainPosition)));

    }

    private Landscape getLandscape() {
        return rain.getLandscape();
    }
}
