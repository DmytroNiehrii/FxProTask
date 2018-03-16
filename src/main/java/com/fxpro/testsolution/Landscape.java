package com.fxpro.testsolution;

import com.fxpro.testsolution.enums.Terrain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@NoArgsConstructor
public class Landscape {
    @Autowired
    private Rain rain;
    @Getter
    private Terrain[][] terrain;

    public void generateLandscapeModel(int[] model) {
        int mountainMaxSize = Utils.getArrayMaxValue(model);
        this.terrain = new Terrain[model.length][mountainMaxSize];

        // Fill all landscape terrain by Air
        for (int i = 0; i < model.length; i++)
            for (int j = 0; j < mountainMaxSize; j++)
                this.terrain[i][j] = Terrain.Air;

        // Fill landscape terrain by Mountain according to terrain
        for (int i = 0; i < model.length; i++)
            for (int j = 0; j < model[i]; j++)
                this.terrain[i][j] = Terrain.Mountain;

        rain.setTerrain(terrain);
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
}
