package com.fxpro.testsolution;

import com.fxpro.testsolution.enums.TerrainType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
                this.terrain[i][j] = TerrainType.Air;

        // Fill landscape terrain by Mountain according to terrain
        for (int i = 0; i < model.length; i++)
            for (int j = 0; j < model[i]; j++)
                this.terrain[i][j] = TerrainType.Mountain;
        //ToDo remove
        printTerrain();
    }


    public int startRain() {
        // Specify where will be rain
        /*for (int i = 0; i < getLandscapeWidth(); i++) {
            rain.createWaterSource(i);
        }*/
        rain.createWaterSource(1);
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

    public synchronized void changeCells(int i1, int j1, int i2, int j2) {
        TerrainType t = this.terrain[i1][j1];
        this.terrain[i1][j1] = this.terrain[i2][j2];
        this.terrain[i2][j2] = t;
    }

    public void printTerrain() {
        System.out.println("================= Terrain ================");
        for (int j = getLandscapeHight(0)-1; j >= 0; j--) {
            for (int i = 0; i < getLandscapeWidth(); i++ ) {

                System.out.print(getTerrainTypeView(getTerrainType(i, j)));
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
    }

    private String getTerrainTypeView(TerrainType terrainType) {
        switch (terrainType) {
            case Mountain: return "X";
            case Air: return " ";
            case Water: return "~";
        }
        return "";
    }

    private Landscape getLandscaoe() {
        return this;
    }
}
