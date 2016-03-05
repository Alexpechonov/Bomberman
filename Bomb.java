package com.alex.bsuir;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Bomb extends Pane {
	Image bombImage = new Image(getClass().getResourceAsStream("res/sprites/bomb_images111.png"));
	ImageView imageView = new ImageView(bombImage);

	public Bomb() {
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		imageView.setViewport(new Rectangle2D(0, 0, 16, 16));
		getChildren().addAll(this.imageView);
	}
	
	public void setBomb() {
		
		double posX = Main.player.getTranslateX() + 17.5 - 120;
		double posY = Main.player.getTranslateY() + 17.5 - 170;
		posX = posX - (posX % 40) + 120 + 5; 
		posY = posY - (posY % 40) + 170 + 5;
		this.setTranslateX(posX);
		this.setTranslateY(posY);
		this.setVisible(true);
	}
	
}
