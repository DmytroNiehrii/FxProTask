package com.fxpro.testsolution;

import com.fxpro.testsolution.enums.TerrainType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.fxpro.testsolution.enums.TerrainType.Air;
import static com.fxpro.testsolution.enums.TerrainType.Water;

@Component
@Scope("prototype")
@NoArgsConstructor
public class Landscape {
    @Autowired
    private Rain rain;
    @Getter
    private TerrainType[][] terrain;

    @PostConstruct
    public void init() {
        this.rain.setLandscape(this);
    }

    public void generateLandscapeModel(int[] model) {
        int mountainMaxSize = Utils.getArrayMaxValue(model);
        this.terrain = new TerrainType[model.length][mountainMaxSize];

        // Fill all landscape terrain by Air
        for (int i = 0; i < model.length; i++)
            for (int j = 0; j < mountainMaxSize; j++)
                this.terrain[i][j] = Air;

        // Fill landscape terrain by Mountain according to terrain
        for (int i = 0; i < model.length; i++)
            for (int j = 0; j < model[i]; j++)
                this.terrain[i][j] = TerrainType.Mountain;
    }

    public int startRain() {
        // Specify where will be rain
        for (int i = 0; i < getLandscapeWidth(); i++) {
            rain.createWaterSource(i);
        }
        rain.start();
        return rain.get();
    }

    public int getLandscapeWidth() {
        return terrain.length;
    }

    public int getLandscapeHight(int i) {
        return terrain[i].length;
    }

    public synchronized TerrainType getTerrainType(int i, int j) {
        return terrain[i][j];
    }

    public synchronized void setTerrainType(int i, int j, TerrainType terrainType) {
        this.terrain[i][j] = terrainType;
    }

    public synchronized boolean putWater(Point point) {
        if (terrain[point.i][point.j] == Air) {
            terrain[point.i][point.j] = Water;
            return true;
        } else return false;
    }
}
