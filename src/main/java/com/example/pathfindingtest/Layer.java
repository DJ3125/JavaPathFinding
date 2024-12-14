package com.example.pathfindingtest;

import java.util.ArrayList;
import java.util.Arrays;

public final class Layer {
    public Layer(int xPosition, int yPosition, Layer below){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.layerBelow = below;
    }

    public int getyPosition() {return this.yPosition;}

    public int getxPosition() {return this.xPosition;}

    public boolean isPotentiallyViable(int xPosition, int yPosition, boolean[][] checkingBool, int width, int height){
        if(xPosition < 0 || xPosition >= width || yPosition < 0 || yPosition >= height){return false;}
        return !checkingBool[yPosition][xPosition];
    }

    public ArrayList<Layer> getViableCanadates(boolean[][] checkingBool, int width, int height){
        int x = this.xPosition, y = this.yPosition;
        ArrayList<Layer> canadates = new ArrayList<>();
        for(int i = x -1; i<= x + 1; i++){
            for(int j = y - 1; j <= y +1; j++){
                if((i != x || j != y) && this.isPotentiallyViable(i, j, checkingBool, width, height)){
                    canadates.add(new Layer(i, j, this));
                    checkingBool[j][i] = true;
                }
            }
        }
        return canadates;
    }

    public boolean reachedGoal(int goalX, int goalY){
        return this.xPosition == goalX && this.yPosition == goalY;
    }

    public int getPathSize(){
        if(this.layerBelow == null){
            return 0;
        }else{return 1 + this.layerBelow.getPathSize();}
    }
    public ArrayList<int[]> getPath(){
        ArrayList<int[]> below = null;
        if(this.layerBelow != null){below = this.layerBelow.getPath();}
        ArrayList<int[]> total = new ArrayList<>();
        if(below != null){total.addAll(below);}
        total.add(new int[]{this.xPosition, this.yPosition});
        return total;
    }

    @Override public boolean equals(Object obj) {
        if(obj instanceof Layer layer) {return this.xPosition == layer.getxPosition() && this.yPosition == layer.getyPosition();}
        return false;
    }

    private final int xPosition, yPosition;
    private final Layer layerBelow;
}
