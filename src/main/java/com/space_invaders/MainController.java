package com.space_invaders;

import java.util.ArrayList;
import java.util.List;

import com.space_invaders.Game.GameLoop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;

public class MainController {
    //To stop game you have to stop the gameLoop, and clear the field
    @FXML
    private AnchorPane field;


    private List<ImageView> hpImages = new ArrayList<>();

    private Label scoreLabel;

    private GameLoop gameLoop;

    private Game game;
    public void initialize() {
        game = new Game(field);
        loadBackToManuButton();

        loadHeartImages();

        loadScore();


        gameLoop = game.new GameLoop(){
            @Override
            public void handle(long now) {
                super.handle(now);
                updateHeartImages();
                scoreLabel.setText(String.valueOf(App.getCoins()));
                
            }
        };
        gameLoop.start();


    }

    public void updateHeartImages(){
        for (int i = 0; i < hpImages.size(); i++) {
            if(i >= game.playerShip.getHp()){
                field.getChildren().remove(hpImages.get(i));
            }
        }
    }

    private void loadBackToManuButton(){
        Button backButton = new Button("Back to Menu");
        backButton.setLayoutX(20);
        backButton.setLayoutY(20);
        backButton.setOnAction(e -> {
            game.clearBulletsAndShips();
            game.stopAllGameLoopsAndTimers();
            field.getChildren().clear(); 
            gameLoop.stop();
            try{
                Game.isGameEnded = true;
                App.setRoot("main-menu.fxml");
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
        field.getChildren().add(backButton);
        backButton.toFront();
    }

    private void loadScore() {
        HBox scoreBox = new HBox();
        Image scoreImage = new Image(getClass().getResourceAsStream("/images/coin.png"));
        ImageView scoreImageView = new ImageView(scoreImage);
        scoreImageView.setFitWidth(30);
        scoreImageView.setPreserveRatio(true);

        scoreLabel = new Label(String.valueOf(App.getCoins()));
        scoreLabel.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-weight: bold;");
        scoreBox.getChildren().addAll(scoreImageView, scoreLabel);
        scoreBox.setLayoutX(Constants.Game.FIELD_WIDTH - 80);
        scoreBox.setLayoutY(Constants.Game.FIELD_HEIGHT - 40);
        field.getChildren().add(scoreBox);
    }

    private void loadHeartImages () {
        Image image = new Image(getClass().getResourceAsStream("/images/heart.png"));
        hpImages.clear(); 

        for (int i = 0; i < game.playerShip.getHp(); i++) {
            ImageView heart = new ImageView(image);
            heart.setFitWidth(30);
            heart.setPreserveRatio(true);
            heart.setLayoutX(10 + i * 30);
            heart.setLayoutY(Constants.Game.FIELD_HEIGHT-40);
            field.getChildren().add(heart);
            hpImages.add(heart); 
        }
    }


}
