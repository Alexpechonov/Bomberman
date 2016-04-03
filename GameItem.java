package com.alex.bsuir;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Class extends StackPane and create blocks
 */
public class GameItem extends StackPane {
  Image itemsImage =
      new Image(this.getClass().getResourceAsStream("res/sprites/bomberman_tiles_sheet.png"));
  ImageView item;

  /**
   * Contains type of blocks
   */
  public enum ItemType {
    PLATFORM, WOODBOX, ULTIMATEBOX, BOMB, INFIRE
  }

  public ItemType type;

  /**
   * @param type of block, position
   * @see GameItem#GameItem(ItemType, int, int)
   */
  public GameItem(ItemType itemType, int x, int y) {


    type = itemType;

    switch (itemType) {
      case PLATFORM:
        item = new ImageView(itemsImage);
        item.setFitHeight(Constants.sizeOfBlocks);
        item.setFitWidth(Constants.sizeOfBlocks);
        setTranslateX(x);
        setTranslateY(y);
        item.setViewport(new Rectangle2D(200, 0, 16, 16));
        break;
      case WOODBOX:
        item = new ImageView(itemsImage);
        item.setFitHeight(Constants.sizeOfBlocks);
        item.setFitWidth(Constants.sizeOfBlocks);
        setTranslateX(x);
        setTranslateY(y);
        item.setViewport(new Rectangle2D(89, 0, 16, 16));
        break;
      case ULTIMATEBOX:
        item = new ImageView(itemsImage);
        item.setFitHeight(Constants.sizeOfBlocks);
        item.setFitWidth(Constants.sizeOfBlocks);
        setTranslateX(x);
        setTranslateY(y);
        item.setViewport(new Rectangle2D(29, 0, 15, 16));
        break;
      default:
        break;
    }
    getChildren().add(item);
    Main.platforms.add(this);
    Main.gameRoot.getChildren().add(this);

  }

  /**
   * Method destroy block
   * 
   * @see GameItem#destroy()
   */
  public void destroy() {
    MySpriteAnimation destroyAnimation =
        new MySpriteAnimation(item, Duration.millis(2000), 8, 7, 89, 0, 16, 16);
    destroyAnimation.play();
  }

}

