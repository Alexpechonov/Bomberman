package com.alex.bsuir;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class GameItem extends StackPane {
	
	Image platformImage = new Image(this.getClass().getResourceAsStream("res/sprites/bomberman_anamies.PNG"));
	Image itemsImage = new Image(this.getClass().getResourceAsStream("res/sprites/bomberman_tiles_sheet .png"));
	ImageView item;
	
	public enum ItemType {
		PLATFORM, WOODBOX, ULTIMATEBOX
	}
	
	public ItemType type;
	
	public GameItem(ItemType itemType, int x, int y) {

		type = itemType;
		
		switch (itemType) {
			case PLATFORM:
				item = new ImageView(platformImage);
				item.setFitHeight(40);
				item.setFitWidth(40);
				setTranslateX(x);
				setTranslateY(y);
				item.setViewport(new Rectangle2D(280, 80, 40, 40));
				break;
			case WOODBOX:
				item = new ImageView(itemsImage);
				item.setFitHeight(40);
				item.setFitWidth(40);
				setTranslateX(x);
				setTranslateY(y);
				item.setViewport(new Rectangle2D(89, 0, 16, 16));
				break;
			case ULTIMATEBOX:
				item = new ImageView(itemsImage);
				item.setFitHeight(40);
				item.setFitWidth(40);
				setTranslateX(x);
				setTranslateY(y);
				item.setViewport(new Rectangle2D(29, 0, 15, 16));
				break;
		}
		getChildren().add(item);
		Main.platforms.add(this);
		Main.gameRoot.getChildren().add(this);

	}
}

