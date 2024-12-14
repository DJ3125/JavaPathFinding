package com.example.pathfindingtest;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public final class HelloController {
    @FXML
    private Label welcomeText;
    @FXML private GridPane gridPane;
    
    private ImageView[][] imageViewGrid;

    public void updateAll(){
//        int[] location = this.path.remove(0);
//        this.player.setxPosition(location[0]);
//        this.player.setyPosition(location[1]);
    }

    public void displayImages(){
        for(ImageView[] i : imageViewGrid){
            for(ImageView j : i){
                j.setImage(this.grass);
            }
        }
        for(Walls i : this.walls){
            imageViewGrid[i.yPosition()][i.xPosition()].setImage(wall);
        }
    }

    @FXML public void initialize(){
        HelloController controller = this;
        this.animationThread = new Thread("animationThread"){
            @Override public void run(){
                long startTime = System.currentTimeMillis();
                int counter = 0, interval = 250;
                controller.updateAll();
                controller.displayImages();
                while(!this.isInterrupted()){
                    while((System.currentTimeMillis() - startTime)/ interval <= counter){Thread.onSpinWait();}
                    counter = (int)((System.currentTimeMillis() - startTime)/ interval);
                    controller.updateAll();
                    controller.displayImages();
                }
            }
        };
        try {
            this.grass = new Image(new FileInputStream("src/main/resources/newGrass.jpg"));
            this.wall = new Image(new FileInputStream("src/main/resources/x.png"));
            this.finder = new Image(new FileInputStream("src/main/resources/Queen.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.imageViewGrid = new ImageView[this.height][this.width];
        for(byte i = 0; i < this.width; i++){
            for(byte j = 0; j < this.height; j++){
                ImageView imageView = new ImageView(grass);
                byte xPosition = j, yPosition = i;
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->{
                    this.walls.add(new Walls(xPosition, yPosition));
                    System.out.println(this.walls);
                });
                HBox hBox = new HBox(imageView);
                this.imageViewGrid[j][i] = imageView;
                double width = this.gridPane.getPrefWidth()/this.width, height = this.gridPane.getPrefHeight()/this.height;
                imageView.setFitHeight(height); imageView.setFitWidth(width);
                hBox.setMinHeight(height); hBox.setMaxHeight(height); hBox.setMaxWidth(width); hBox.setMinWidth(width); hBox.setAlignment(Pos.CENTER);
                this.gridPane.add(hBox, i, j);
            }
        }

        this.gridPane.setGridLinesVisible(true);
//        this.animationThread.start();
    }

    @FXML public void onHelloButtonClick() {
        HelloController controller = this;
        System.out.println(Runtime.getRuntime().freeMemory());
        for(byte i = 0; i < 1; i++){
            byte test = i;
            new Thread(){
                @Override
                public void run() {
                    long time = System.currentTimeMillis();
                    ArrayList<Layer> trying = new ArrayList<>();
                    Layer smallest = null;
                    boolean[][] isOccupied = new boolean[controller.height][controller.width];
                    for(Walls i : controller.walls){isOccupied[i.yPosition()][i.xPosition()] = true;}
                    Layer start = new Layer(controller.player.getxPosition(), controller.player.getyPosition(), null);
                    trying.add(start);
                    isOccupied[start.getyPosition()][start.getxPosition()] = true;
                    do{
                        ArrayList<Layer> replacement = new ArrayList<>();
                        for(Layer i : trying){replacement.addAll(i.getViableCanadates(isOccupied, controller.width, controller.height));}
                        trying = replacement;
                        System.out.println(Runtime.getRuntime().freeMemory());
                        int counter = 0;
                        while(counter < trying.size()){
                            if(trying.get(counter).reachedGoal(goalX, goalY)){
                                if(smallest == null || smallest.getPathSize() > trying.get(counter).getPathSize()){
                                    smallest = trying.get(counter);
                                }
                                trying.remove(counter);

                            }else{
                                if(smallest != null && trying.get(counter).getPathSize() > smallest.getPathSize()){
                                    trying.remove(counter);
                                }else{
                                    counter++;
                                }
                            }
                        }
                    }while(!trying.isEmpty());
                    System.out.println();
                    System.out.println("Path" + test + ": " + (System.currentTimeMillis() - time));
                    System.out.println(smallest != null);
                    System.out.println();
                    if(smallest != null){
                        for(int[] i : smallest.getPath()){
                            System.out.print(Arrays.toString(i));
                        }
                    }
//                }
                }
            }.start();
        }

    }

    
    private Thread animationThread;
    
    private final int height = 100, width = 100 , goalX = 0, goalY = 0;

    private final ArrayList<Walls> walls = new ArrayList<>();

    private final PathFinder player = new PathFinder(this.width - 3, this.height - 1);
    private final ArrayList<int[]> path = new ArrayList<>();
    private Image grass, wall, finder;
}