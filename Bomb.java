package com.alex.bsuir;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bomb extends Pane {
	
	private static int[] pos = new int[6];
	
	Image bombImage = new Image(getClass().getResourceAsStream("res/sprites/bomb_images_new.png"));
	ImageView imageView = new ImageView(bombImage);
	ImageView hierView = new ImageView(bombImage);
	ImageView lowView = new ImageView(bombImage);
	ImageView leftView = new ImageView(bombImage);
	ImageView rightView = new ImageView(bombImage);
	private MySpriteAnimation firstAnimation, centerAnimation, leftAnimation, rightAnimation, lowAnimation, hierAnimation;
	
	private boolean ready = true;
	private int numberOfBomb;
	
	private boolean left = true, right = true, hier = true, low = true;
	
	public Bomb(int num) {
		numberOfBomb = num;
		imageView.setFitHeight(40);
		imageView.setFitWidth(40);
		
		leftView.setFitHeight(40);
		leftView.setFitWidth(40);
		rightView.setFitHeight(40);
		rightView.setFitWidth(40);
		hierView.setFitHeight(40);
		hierView.setFitWidth(40);
		lowView.setFitHeight(40);
		lowView.setFitWidth(40);
		
		firstAnimation = new MySpriteAnimation(imageView, Duration.millis(3000), 10, 9, 0, 0, 16, 15);
		firstAnimation.SetType(1);
		firstAnimation.SetNumberOfBomb(numberOfBomb);
		centerAnimation = new MySpriteAnimation(imageView, Duration.millis(700), 5, 4, 0, 15, 16, 16);
		centerAnimation.SetType(2);
		centerAnimation.SetNumberOfBomb(numberOfBomb);
		
		leftAnimation = new MySpriteAnimation(leftView, Duration.millis(700), 5, 4, 0, 31, 16, 16);
		leftAnimation.SetNumberOfBomb(numberOfBomb);
		leftView.setViewport(new Rectangle2D(0, 31, 16, 16));
		leftView.setLayoutX(-40);
		leftView.setVisible(false);
		getChildren().addAll(leftView);
		
		rightAnimation = new MySpriteAnimation(rightView, Duration.millis(700), 5, 4, 0, 47, 16, 16);
		rightAnimation.SetNumberOfBomb(numberOfBomb);
		rightView.setViewport(new Rectangle2D(0, 47, 16, 16));
		rightView.setLayoutX(40);
		rightView.setScaleX(1);
		rightView.setVisible(false);
		getChildren().addAll(rightView);
		
		hierAnimation = new MySpriteAnimation(hierView, Duration.millis(700), 5, 4, 0, 63, 16, 16);
		hierAnimation.SetNumberOfBomb(numberOfBomb);
		hierView.setViewport(new Rectangle2D(0, 63, 16, 16));
		hierView.setLayoutY(-40);
		hierView.setVisible(false);
		getChildren().addAll(hierView);
		
		lowAnimation = new MySpriteAnimation(lowView, Duration.millis(700), 5, 4, 0, 79, 16, 16);
		lowAnimation.SetNumberOfBomb(numberOfBomb);
		lowView.setViewport(new Rectangle2D(0, 79, 16, 16));
		lowView.setLayoutY(40);
		lowView.setVisible(false);
		getChildren().addAll(lowView);
		
		imageView.setViewport(new Rectangle2D(0, 0, 16, 15));
		getChildren().addAll(this.imageView);
		for (int i = 0; i < 6; i++) {
			pos[i] = -1;
		}
	}

	public boolean isReady() {
		return ready;
	}
	public void startUse() {
		ready = false;
	}
	public void endOfUse() {
		ready = true;
	}
	
	public int GetNumber() {
		return numberOfBomb;
	}
	
	public void setBomb() {
		
		ready = false;
		double posX = Main.player.getTranslateX() + 17 - 120;
		double posY = Main.player.getTranslateY() + 17 - 170;
		posX = posX - (posX % 40) + 120; 
		posY = posY - (posY % 40) + 170;
		
		for(int i = 0; i < 6; i+=2) {
			if( pos[i] == posX && pos[i+1] == posY) {
				Main.bomb[numberOfBomb - 1].setVisible(false);
				ready = true;
				return;
			}
		}
		
		Main.platforms.get((int) ((((posY - 170) / 40) * 19) + ((posX - 120) / 40))).type = GameItem.ItemType.BOMB;
		
		System.out.println("BOMB");
		System.out.print(numberOfBomb);
		
		pos[numberOfBomb * 2 - 2] = (int) posX;
		pos[numberOfBomb * 2 - 1] = (int) posY;
		
		Main.bomb[numberOfBomb - 1].setTranslateX(posX);
		Main.bomb[numberOfBomb - 1].setTranslateY(posY);
		Main.bomb[numberOfBomb - 1].setVisible(true);
		firstAnimation.play();
	}
	public void detonation() {
		firstAnimation.stop();
		imageView.setViewport(new Rectangle2D(0, 16, 16, 16));
		centerAnimation.play();
		Main.platforms.get((int) ((((pos[numberOfBomb * 2 - 1] - 170) / 40) * 19) + ((pos[numberOfBomb * 2 - 2] - 120) / 40))).type = GameItem.ItemType.INFIRE;
		Main.player.checkPosition(pos[numberOfBomb * 2 - 2], pos[numberOfBomb * 2 - 1]);
		
		if ((pos[numberOfBomb * 2 - 2] - 120) == 0) left = false;
		if ((pos[numberOfBomb * 2 - 2] - 120) == 720) right = false;
		if ((pos[numberOfBomb * 2 - 1] - 170) == 0) hier = false;
		if ((pos[numberOfBomb * 2 - 1] - 170) == 320) low = false;
		
		for( GameItem platform: Main.platforms) {
			if (platform.type == GameItem.ItemType.ULTIMATEBOX || platform.type == GameItem.ItemType.WOODBOX) {
				if( this.getTranslateX() - 40 == platform.getTranslateX() && this.getTranslateY() == platform.getTranslateY() && left == true) {
					if (platform.type == GameItem.ItemType.ULTIMATEBOX)
					left = false;
					if (platform.type == GameItem.ItemType.WOODBOX)
					platform.destroy();
				} else if ( this.getTranslateX() + 40 == platform.getTranslateX() && this.getTranslateY() == platform.getTranslateY() && right == true) {
					if (platform.type == GameItem.ItemType.ULTIMATEBOX)
					right = false;
					if (platform.type == GameItem.ItemType.WOODBOX)
					platform.destroy();
				} else if ( this.getTranslateX() == platform.getTranslateX() && this.getTranslateY() - 40 == platform.getTranslateY() && hier == true) {
					if (platform.type == GameItem.ItemType.ULTIMATEBOX)
					hier = false;
					if (platform.type == GameItem.ItemType.WOODBOX)
					platform.destroy();
				} else if ( this.getTranslateX() == platform.getTranslateX() && this.getTranslateY() + 40 == platform.getTranslateY() && low == true) {
					if (platform.type == GameItem.ItemType.ULTIMATEBOX)
					low = false;
					if (platform.type == GameItem.ItemType.WOODBOX)
					platform.destroy();
				}
			}
		}
		
		if ( left == true) {
		leftAnimation.play();
		leftView.setVisible(true);
		Main.platforms.get((int) ((((pos[numberOfBomb * 2 - 1] - 170) / 40) * 19) + ((pos[numberOfBomb * 2 - 2] - 40 - 120) / 40))).type = GameItem.ItemType.INFIRE;
		Main.player.checkPosition(pos[numberOfBomb * 2 - 2] - 40, pos[numberOfBomb * 2 - 1]);
		}
		if (right == true) {
		rightAnimation.play();
		rightView.setVisible(true);
		Main.platforms.get((int) ((((pos[numberOfBomb * 2 - 1] - 170) / 40) * 19) + ((pos[numberOfBomb * 2 - 2] + 40 - 120) / 40))).type = GameItem.ItemType.INFIRE;
		Main.player.checkPosition(pos[numberOfBomb * 2 - 2] - 40, pos[numberOfBomb * 2 - 1] );
		}
		if (hier == true) {
		hierAnimation.play();
		hierView.setVisible(true);
		Main.platforms.get((int) ((((pos[numberOfBomb * 2 - 1] - 40 - 170) / 40) * 19) + ((pos[numberOfBomb * 2 - 2] - 120) / 40))).type = GameItem.ItemType.INFIRE;
		Main.player.checkPosition(pos[numberOfBomb * 2 - 2], pos[numberOfBomb * 2 - 1] - 40);
		}
		if (low == true) {
		lowAnimation.play();
		lowView.setVisible(true);
		Main.platforms.get((int) ((((pos[numberOfBomb * 2 - 1] + 40 - 170) / 40) * 19) + ((pos[numberOfBomb * 2 - 2] - 120) / 40))).type = GameItem.ItemType.INFIRE;
		Main.player.checkPosition(pos[numberOfBomb * 2 - 2], pos[numberOfBomb * 2 - 1] + 40);
		}	
	}
	public void endOfDetonation() {
		Main.bomb[numberOfBomb - 1].setVisible(false);
		Main.bomb[numberOfBomb - 1].setTranslateX(-40);
		Main.bomb[numberOfBomb - 1].setTranslateY(-40);
		
		Main.platforms.get((int) ((((pos[numberOfBomb * 2 - 1] - 170) / 40) * 19) + ((pos[numberOfBomb * 2 - 2] - 120) / 40))).type = GameItem.ItemType.PLATFORM;
		
		if ( left == true) {
			leftAnimation.stop();
			leftView.setVisible(false);
			Main.platforms.get((int) ((((pos[numberOfBomb * 2 - 1] - 170) / 40) * 19) + ((pos[numberOfBomb * 2 - 2] - 40 - 120) / 40))).type = GameItem.ItemType.PLATFORM;
			}
			if (right == true) {
			rightAnimation.stop();
			rightView.setVisible(false);
			Main.platforms.get((int) ((((pos[numberOfBomb * 2 - 1] - 170) / 40) * 19) + ((pos[numberOfBomb * 2 - 2] + 40 - 120) / 40))).type = GameItem.ItemType.PLATFORM;
			}
			if (hier == true) {
			hierAnimation.stop();
			hierView.setVisible(false);
			Main.platforms.get((int) ((((pos[numberOfBomb * 2 - 1] - 40 - 170) / 40) * 19) + ((pos[numberOfBomb * 2 - 2] - 120) / 40))).type = GameItem.ItemType.PLATFORM;
			}
			if (low == true) {
			lowAnimation.stop();
			lowView.setVisible(false);
			Main.platforms.get((int) ((((pos[numberOfBomb * 2 - 1] + 40 - 170) / 40) * 19) + ((pos[numberOfBomb * 2 - 2] - 120) / 40))).type = GameItem.ItemType.PLATFORM;
			}
		
			left = right = hier = low = true;
		
		pos[numberOfBomb * 2 - 2] = -1;
		pos[numberOfBomb * 2 - 1] = -1;
		ready = true;
	}
	
}
