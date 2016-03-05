package com.alex.bsuir;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Character extends Pane {
	Image bomberImage = new Image(getClass().getResourceAsStream("res/sprites/bomberman_full_models.png"));
	ImageView imageView = new ImageView(bomberImage);
	int count = 3;
	int columns = 3;
	int offsetX = 18;
	int offsetY = 0;
	int width = 18;
	int height = 20;
	public MySpriteAnimation animationRaL, animationUp, animationDown;
	
	public Character() {
		imageView.setFitHeight(35);
		imageView.setFitWidth(35);
		imageView.setViewport(new Rectangle2D(offsetX - 18, offsetY + 20, width, height));
		animationRaL = new MySpriteAnimation(this.imageView, Duration.millis(350), count, columns, offsetX, offsetY, width, height);
		animationUp = new MySpriteAnimation(this.imageView, Duration.millis(350), count, columns, offsetX, offsetY + 40, width, height);
		animationDown = new MySpriteAnimation(this.imageView, Duration.millis(350), count, columns, offsetX, offsetY + 20, width, height);
		getChildren().addAll(this.imageView);
	}
	
	public void moveX(int value) {
		boolean movingRight = value > 0;
		for (int i = 0; i<Math.abs(value); i++) {
			for (GameItem platform: Main.platforms) {
				if(this.getBoundsInParent().intersects(platform.getBoundsInParent())){
					if (movingRight) {
						if((this.getTranslateX() + 35 == platform.getTranslateX()) && (platform.type != GameItem.ItemType.PLATFORM)) {
							this.setTranslateX(this.getTranslateX() - 1);
							if (this.getTranslateY() + 30 <= platform.getTranslateY())
								this.setTranslateY(this.getTranslateY() - (this.getTranslateY() + 36 - platform.getTranslateY()));
							if (this.getTranslateY() >= platform.getTranslateY() + 35)
								this.setTranslateY(this.getTranslateY() + (platform.getTranslateY() + 41 - this.getTranslateY()));
							return;
						}
					} else {
						if ((this.getTranslateX() == platform.getTranslateX() + 40) && (platform.type != GameItem.ItemType.PLATFORM)) {
							this.setTranslateX(this.getTranslateX() + 1);
							if (this.getTranslateY() + 30 <= platform.getTranslateY())
								this.setTranslateY(this.getTranslateY() - (this.getTranslateY() + 36 - platform.getTranslateY()));
							if (this.getTranslateY() >= platform.getTranslateY() + 35)
								this.setTranslateY(this.getTranslateY() + (platform.getTranslateY() + 41 - this.getTranslateY()));
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
					if (movingDown) {
						if((this.getTranslateY() + 35 == platform.getTranslateY()) && (platform.type != GameItem.ItemType.PLATFORM)) {
							this.setTranslateY(this.getTranslateY() - 1);
							if( this.getTranslateX() >= platform.getTranslateX() + 35)
								this.setTranslateX(this.getTranslateX() + (platform.getTranslateX() + 41 - this.getTranslateX()));
							if( this.getTranslateX() + 30 <= platform.getTranslateX())
								this.setTranslateX(this.getTranslateX() - (this.getTranslateX() + 36 - platform.getTranslateX()));
							return;
						}
					} else {
						if ((this.getTranslateY() == platform.getTranslateY() + 40) && (platform.type != GameItem.ItemType.PLATFORM)) {
							this.setTranslateY(this.getTranslateY() + 1);
							if( this.getTranslateX() >= platform.getTranslateX() + 35)
								this.setTranslateX(this.getTranslateX() + (platform.getTranslateX() + 41 - this.getTranslateX()));
							if( this.getTranslateX() + 30 <= platform.getTranslateX())
								this.setTranslateX(this.getTranslateX() - (this.getTranslateX() + 36 - platform.getTranslateX()));
							return;
							}
						}
					}
				}
			this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
			}
		}
	}


