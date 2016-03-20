package com.alex.bsuir;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Character extends Pane {
	
	private boolean alive;
	
	Image bomberImage = 
			new Image(getClass().getResourceAsStream("res/sprites/bomberman_full_models.png"));
	ImageView imageView = new ImageView(bomberImage);
	int count = 3;
	int columns = 3;
	int offsetX = 18;
	int offsetY = 0;
	int width = 18;
	int height = 20;
	public MySpriteAnimation animationRaL, animationUp, animationDown, deathAnimation;
	
	public Character() {
		alive = true;
		imageView.setFitHeight(34);
		imageView.setFitWidth(34);
		imageView.setViewport(new Rectangle2D(offsetX - 18, offsetY + 20, width, height));
		animationRaL = new MySpriteAnimation(this.imageView, Duration.millis(350), 
				count, columns, offsetX, offsetY, width, height);
		animationUp = new MySpriteAnimation(this.imageView, Duration.millis(350),
				count, columns, offsetX, offsetY + 40, width, height);
		animationDown = new MySpriteAnimation(this.imageView, Duration.millis(350), 
				count, columns, offsetX, offsetY + 20, width, height);
		deathAnimation = new MySpriteAnimation(this.imageView, Duration.millis(2000),
				10, 9, 0, 60, 23, height);
		getChildren().addAll(this.imageView);
	}
	
	public boolean isAlive() {
		return alive;
	}
	public void moveX(int value) {
		boolean movingRight = value > 0;
		for (int i = 0; i<Math.abs(value); i++) {
			for (GameItem platform: Main.platforms) {
				if(this.getBoundsInParent().intersects(platform.getBoundsInParent())){
					if(platform.type == GameItem.ItemType.INFIRE)	
					{setDeath();return;}
					if (movingRight) {
						if((this.getTranslateX() + 34 == platform.getTranslateX()) && 
								(platform.type != GameItem.ItemType.PLATFORM) && 
								(platform.type != GameItem.ItemType.INFIRE)) {
							this.setTranslateX(this.getTranslateX() - 1);
							if (this.getTranslateY() + 29 <= platform.getTranslateY())
								this.setTranslateY(this.getTranslateY() - 
										(this.getTranslateY() + 35 - platform.getTranslateY()));
							if (this.getTranslateY() >= platform.getTranslateY() + 34)
								this.setTranslateY(this.getTranslateY() + 
										(platform.getTranslateY() + 41 - this.getTranslateY()));
							return;
						}
					} else {
						if ((this.getTranslateX() == platform.getTranslateX() + 40) && 
								(platform.type != GameItem.ItemType.PLATFORM) && 
								(platform.type != GameItem.ItemType.INFIRE)) {
							this.setTranslateX(this.getTranslateX() + 1);
							if (this.getTranslateY() + 29 <= platform.getTranslateY())
								this.setTranslateY(this.getTranslateY() - 
										(this.getTranslateY() + 35 - platform.getTranslateY()));
							if (this.getTranslateY() >= platform.getTranslateY() + 34)
								this.setTranslateY(this.getTranslateY() + 
										(platform.getTranslateY() + 41 - this.getTranslateY()));
							return;
							}
						}
					}
				}
			this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
			}	
		}
	public void moveY(int value) {
		boolean movingDown = value > 0;
		for (int i = 0; i<Math.abs(value); i++) {
			for (GameItem platform: Main.platforms) {
				if(this.getBoundsInParent().intersects(platform.getBoundsInParent())){
					if(platform.type == GameItem.ItemType.INFIRE)	
					{setDeath();return;}
					if (movingDown) {
						if((this.getTranslateY() + 34 == platform.getTranslateY()) && 
								(platform.type != GameItem.ItemType.PLATFORM) && 
								(platform.type != GameItem.ItemType.INFIRE)) {
							this.setTranslateY(this.getTranslateY() - 1);
							if( this.getTranslateX() >= platform.getTranslateX() + 34)
								this.setTranslateX(this.getTranslateX() + 
										(platform.getTranslateX() + 41 - this.getTranslateX()));
							if( this.getTranslateX() + 30 <= platform.getTranslateX())
								this.setTranslateX(this.getTranslateX() - 
										(this.getTranslateX() + 35 - platform.getTranslateX()));
							return;
						}
					} else {
						if ((this.getTranslateY() == platform.getTranslateY() + 40) && 
								(platform.type != GameItem.ItemType.PLATFORM) && 
								(platform.type != GameItem.ItemType.INFIRE)) {
							this.setTranslateY(this.getTranslateY() + 1);
							if( this.getTranslateX() >= platform.getTranslateX() + 34)
								this.setTranslateX(this.getTranslateX() + 
										(platform.getTranslateX() + 41 - this.getTranslateX()));
							if( this.getTranslateX() + 29 <= platform.getTranslateX())
								this.setTranslateX(this.getTranslateX() - 
										(this.getTranslateX() + 35 - platform.getTranslateX()));
							return;
							}
						}
					}
				}
			this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
			}
		}
	public void checkPosition(int x,int y) {
		if(this.getBoundsInParent().intersects(x, y, 40, 40))
			setDeath();
	}
	public void setDeath(){
		if (alive == true) {
			alive = false;
			deathAnimation.play();
			Main.createEndOfGame();
			
		}
	}
}


