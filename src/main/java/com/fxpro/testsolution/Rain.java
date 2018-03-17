package com.fxpro.testsolution;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

@Component
@Scope("prototype")
@NoArgsConstructor
@Getter
@Setter
public class Rain implements Supplier<Integer> {
    private Landscape landscape;
    private Set<WaterSource> waterSources = new HashSet<>();
    private Set<CompletableFuture<Integer>> waterSourceVolumes = new HashSet<>();

    public WaterSource createWaterSource(int terrainPosition) {
        WaterSource waterSource = new WaterSource();
        waterSource.setRain(this);

        waterSource.setTerrainPosition(terrainPosition);
        waterSources.add(waterSource);

        return waterSource;
    }

    public void start() {
        waterSources.forEach(waterSource -> waterSourceVolumes.add(CompletableFuture.supplyAsync(waterSource)));
    }


    @Override
    public Integer get() {
        return waterSourceVolumes.stream().map(feature -> featureToInteger(feature)).reduce((left, right) -> left + right).get();
    }

    public Integer featureToInteger(CompletableFuture<Integer> feature) {
        try {
            return feature.get();
        } catch (Exception e) {
            return 0;
        }
    }
}
