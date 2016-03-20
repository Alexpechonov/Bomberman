package com.alex.bsuir;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Enemy extends Pane{
    Image enemyImage = 
    		new Image(getClass().getResourceAsStream("res/sprites/bomberman_enemies_sheet.png"));
	ImageView imageView = new ImageView(enemyImage);
	MySpriteAnimation animation, deathAnimation;
	
	private boolean isAlive;
	private boolean ready;
	
	private int maxX;
	private int minX;
	private int maxY;
	private int minY;
	private int speedX;
	private int speedY;
	
	public Enemy() {
		ready = true;
		isAlive = true;
		imageView.setFitHeight(38);
		imageView.setFitWidth(38);
		imageView.setViewport(new Rectangle2D(0, 0, 16, 16));
		animation = new MySpriteAnimation(imageView, Duration.millis(1000), 7, 6, 0, 0, 16, 16);
		deathAnimation = new MySpriteAnimation(
				imageView, Duration.millis(1500), 7, 6, 0, 16, 16, 16);
		getChildren().addAll(this.imageView);
	}
	
	public void moveX(int value) {
		boolean movingRight = value > 0;
		for (int i = 0; i<Math.abs(value); i++) {
			if(this.getBoundsInParent().intersects(Main.player.getBoundsInParent())) 
				Main.player.setDeath();
			for (GameItem platform: Main.platforms) {
				if(this.getBoundsInParent().intersects(platform.getBoundsInParent())){
					if(platform.type == GameItem.ItemType.INFIRE)	{setDeath();return;}
					if (movingRight) {
						if((this.getTranslateX() + 38 == platform.getTranslateX()) &&
								(platform.type != GameItem.ItemType.PLATFORM) &&
								(platform.type != GameItem.ItemType.INFIRE)) {
							this.setTranslateX(this.getTranslateX() - 1);
							speedX = -speedX;
							return;
						}
					} else {
						if ((this.getTranslateX() == platform.getTranslateX() + 40) &&
								(platform.type != GameItem.ItemType.PLATFORM) &&
								(platform.type != GameItem.ItemType.INFIRE)) {
							this.setTranslateX(this.getTranslateX() + 1);
							speedX = -speedX;
							return;
							}
						}
					}
				}
			this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
			if((this.getTranslateX() - 120 - 1) % 40 == 0 && 
					(this.getTranslateY() - 170 - 1) % 40 == 0) {
					switch((int)(Math.random() * 6)) {
					case 1:
						if(this.getTranslateY() > 170 + 1) {
							if( Main.platforms.get((int)
									((((this.getTranslateY() - 1 - 40 - 170) / 40) * 19) + 
											((this.getTranslateX() - 1 - 120) / 40))).type == 
											GameItem.ItemType.PLATFORM) {
								speedY = -Math.abs(speedX);
								speedX = 0;
								return;
							}
						}
					case 2:
						if(this.getTranslateY() < 170 + 8 * 40 + 1) {
							if( Main.platforms.get((int) 
									((((this.getTranslateY() - 1 + 40 - 170) / 40) * 19) + 
											((this.getTranslateX() - 1 - 120) / 40))).type == 
											GameItem.ItemType.PLATFORM) {
								speedY = Math.abs(speedX);
								speedX = 0;
								return;
							}
						}
					}
				
			}
			}	
		}
	public void moveY(int value) {
		boolean movingDown = value > 0;
		for (int i = 0; i<Math.abs(value); i++) {
			if(this.getBoundsInParent().intersects(Main.player.getBoundsInParent())) 
				Main.player.setDeath();
			for (GameItem platform: Main.platforms) {
				if(this.getBoundsInParent().intersects(platform.getBoundsInParent())){
					if(platform.type == GameItem.ItemType.INFIRE)	{setDeath();return;}
					if (movingDown) {
						if((this.getTranslateY() + 38 == platform.getTranslateY()) && 
								(platform.type != GameItem.ItemType.PLATFORM) && 
								(platform.type != GameItem.ItemType.INFIRE)) {
							this.setTranslateY(this.getTranslateY() - 1);
							speedY = -speedY;
							return;
						}
					} else {
						if ((this.getTranslateY() == platform.getTranslateY() + 40) && 
								(platform.type != GameItem.ItemType.PLATFORM) && 
								(platform.type != GameItem.ItemType.INFIRE)) {
							this.setTranslateY(this.getTranslateY() + 1);
							speedY = -speedY;
							return;
							}
						}
					}
				}
			this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
			if((this.getTranslateX() - 120 - 1) % 40 == 0 && 
					(this.getTranslateY() - 170 - 1) % 40 == 0) { 
					switch((int)(Math.random() * 6)) {
					case 1:	
						if (this.getTranslateX() > 120 + 1) {
							if( Main.platforms.get((int) 
									((((this.getTranslateY() - 1 - 170) / 40) * 19) + 
									((this.getTranslateX() - 1 - 40 - 120) / 40))).type == 
									GameItem.ItemType.PLATFORM) {
								speedX = -Math.abs(speedY);
								speedY = 0;
								return;
							}
						}
					case 2:
						if(this.getTranslateX() < 120 + 18 * 40 + 1) {
							if( Main.platforms.get((int) 
									((((this.getTranslateY() - 1 - 170) / 40) * 19) + 
									((this.getTranslateX() - 1 + 40 - 120) / 40))).type == 
									GameItem.ItemType.PLATFORM) {
								speedX = Math.abs(speedY);
								speedY = 0;
								return;
							}
						}
					}
				
			}
			}
		}
	
	private void setDeath() {
		animation.stop();
		isAlive = false;
		deathAnimation.play();
	}
	public boolean getAlive() {
		return isAlive;
	}
	
	public void move() {
		if (isAlive == true && ready == true) {
			ready = false;
			if (this.getTranslateX() + 38 >= maxX || this.getTranslateX() <= minX) 
				speedX = -speedX;
			if (this.getTranslateY() + 38 >= maxY || this.getTranslateY() <= minY) 
				speedY = -speedY;
			animation.play();
			if(speedX != 0)
			moveX(speedX);
			if(speedY != 0)
			moveY(speedY);
			ready = true;
		}
		
	}

	public void setSpeed(int x, int y){
		speedX = x;
		speedY = y;
	}
	public void setArea(int x1, int x2, int y1, int y2) {
		minX = x1;
		maxX = x2;
		minY = y1;
		maxY = y2;
	}
	
}
