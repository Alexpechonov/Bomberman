package com.alex.bsuir;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Class extends Pane and describes the logic of bombs
 */
public class Bomb extends Pane {

  /**
   * Variable contains positions of bombs
   */
  private static int[] pos = new int[6];

  Image bombImage = new Image(getClass().getResourceAsStream("res/sprites/bomb_images_new.png"));
  ImageView imageView = new ImageView(bombImage);
  ImageView hierView = new ImageView(bombImage);
  ImageView lowView = new ImageView(bombImage);
  ImageView leftView = new ImageView(bombImage);
  ImageView rightView = new ImageView(bombImage);
  private MySpriteAnimation firstAnimation, centerAnimation, leftAnimation, rightAnimation,
      lowAnimation, hierAnimation;

  private boolean ready = true;
  private int numberOfBomb;

  private boolean left = true, right = true, hier = true, low = true;

  /**
   * @param num number of bomb
   * @see Bomb#Bomb(int)
   */
  public Bomb(int num) {
    numberOfBomb = num;
    imageView.setFitHeight(Constants.sizeOfBlocks);
    imageView.setFitWidth(Constants.sizeOfBlocks);

    leftView.setFitHeight(Constants.sizeOfBlocks);
    leftView.setFitWidth(Constants.sizeOfBlocks);
    rightView.setFitHeight(Constants.sizeOfBlocks);
    rightView.setFitWidth(Constants.sizeOfBlocks);
    hierView.setFitHeight(Constants.sizeOfBlocks);
    hierView.setFitWidth(Constants.sizeOfBlocks);
    lowView.setFitHeight(Constants.sizeOfBlocks);
    lowView.setFitWidth(Constants.sizeOfBlocks);

    firstAnimation = new MySpriteAnimation(imageView, Duration.millis(3000), 10, 9, 0, 0, 16, 15);
    firstAnimation.SetType(1);
    firstAnimation.SetNumberOfBomb(numberOfBomb);
    centerAnimation = new MySpriteAnimation(imageView, Duration.millis(700), 5, 4, 0, 15, 16, 16);
    centerAnimation.SetType(2);
    centerAnimation.SetNumberOfBomb(numberOfBomb);

    leftAnimation = new MySpriteAnimation(leftView, Duration.millis(700), 5, 4, 0, 31, 16, 16);
    leftAnimation.SetNumberOfBomb(numberOfBomb);
    leftView.setViewport(new Rectangle2D(0, 31, 16, 16));
    leftView.setLayoutX(-Constants.sizeOfBlocks);
    leftView.setVisible(false);
    getChildren().addAll(leftView);

    rightAnimation = new MySpriteAnimation(rightView, Duration.millis(700), 5, 4, 0, 47, 16, 16);
    rightAnimation.SetNumberOfBomb(numberOfBomb);
    rightView.setViewport(new Rectangle2D(0, 47, 16, 16));
    rightView.setLayoutX(Constants.sizeOfBlocks);
    rightView.setScaleX(1);
    rightView.setVisible(false);
    getChildren().addAll(rightView);

    hierAnimation = new MySpriteAnimation(hierView, Duration.millis(700), 5, 4, 0, 63, 16, 16);
    hierAnimation.SetNumberOfBomb(numberOfBomb);
    hierView.setViewport(new Rectangle2D(0, 63, 16, 16));
    hierView.setLayoutY(-Constants.sizeOfBlocks);
    hierView.setVisible(false);
    getChildren().addAll(hierView);

    lowAnimation = new MySpriteAnimation(lowView, Duration.millis(700), 5, 4, 0, 79, 16, 16);
    lowAnimation.SetNumberOfBomb(numberOfBomb);
    lowView.setViewport(new Rectangle2D(0, 79, 16, 16));
    lowView.setLayoutY(Constants.sizeOfBlocks);
    lowView.setVisible(false);
    getChildren().addAll(lowView);

    imageView.setViewport(new Rectangle2D(0, 0, 16, 15));
    getChildren().addAll(this.imageView);
    for (int i = 0; i < 6; i++) {
      pos[i] = -1;
    }
  }

  /**
   * Method return readiness of bomb
   * 
   * @see Bomb#isReady()
   * @return boolean true if bomb is ready
   */
  public boolean isReady() {
    return ready;
  }

  /**
   * Method return number of bomb
   * 
   * @see Bomb#GetNumber()
   * @return int number of bomb
   */
  public int GetNumber() {
    return numberOfBomb;
  }

  public void setBomb(int numBomb, int x1, int x2, int x3, int y1, int y2, int y3) {

    ready = false;
    int posX = x1 + x2 * 10 + x3 * 100;
    int posY = y1 + y2 * 10 + y3 * 100;

    pos[numberOfBomb * 2 - 2] = (int) posX;
    pos[numberOfBomb * 2 - 1] = (int) posY;

    Main.platforms.get((int) ((((posY - Constants.offsetUp) / Constants.sizeOfBlocks)
        * Constants.BlocksInHorizontal))).type = GameItem.ItemType.BOMB;
    Main.bomb[numBomb].setTranslateX(posX);
    Main.bomb[numBomb].setTranslateY(posY);
    Main.bomb[numBomb].setVisible(true);
    firstAnimation.play();

  }

  /**
   * Method set bomb
   * 
   * @see Bomb#setBomb()
   */
  public void setBomb() {

    ready = false;
    int posX =
        (int) (Main.player.getTranslateX() + Constants.sizeOfCharacter / 2 - Constants.offsetLeft);
    int posY =
        (int) (Main.player.getTranslateY() + Constants.sizeOfCharacter / 2 - Constants.offsetUp);
    posX = posX - (posX % Constants.sizeOfBlocks) + Constants.offsetLeft;
    posY = posY - (posY % Constants.sizeOfBlocks) + Constants.offsetUp;
    GameItem plat =
        Main.platforms.get((int) ((((posY - Constants.offsetUp) / Constants.sizeOfBlocks)
            * Constants.BlocksInHorizontal)
            + ((posX - Constants.offsetLeft) / Constants.sizeOfBlocks)));
    if (plat.type == GameItem.ItemType.BOMB) {
      Main.bomb[numberOfBomb - 1].setVisible(false);
      ready = true;
      return;
    }

    pos[numberOfBomb * 2 - 2] = (int) posX;
    pos[numberOfBomb * 2 - 1] = (int) posY;

    plat.type = GameItem.ItemType.BOMB;
    Main.bomb[numberOfBomb - 1].setTranslateX(posX);
    Main.bomb[numberOfBomb - 1].setTranslateY(posY);
    Main.bomb[numberOfBomb - 1].setVisible(true);
    firstAnimation.play();
    Constants.save.saveMove(5);
    Constants.save.saveMove(numberOfBomb);
    Constants.save.saveMove(posX % 10);
    posX /= 10;
    Constants.save.saveMove(posX % 10);
    posX /= 10;
    Constants.save.saveMove(posX % 10);

    Constants.save.saveMove(posY % 10);
    posY /= 10;
    Constants.save.saveMove(posY % 10);
    posY /= 10;
    Constants.save.saveMove(posY % 10);

  }

  /**
   * Method start detonation
   * 
   * @see Bomb#detonation()
   */
  public void detonation() {
    firstAnimation.stop();
    imageView.setViewport(new Rectangle2D(0, 16, 16, 16));
    centerAnimation.play();
    Main.platforms
        .get((int) ((((pos[numberOfBomb * 2 - 1] - Constants.offsetUp) / Constants.sizeOfBlocks)
            * Constants.BlocksInHorizontal)
            + ((pos[numberOfBomb * 2 - 2] - Constants.offsetLeft) / Constants.sizeOfBlocks))).type =
                GameItem.ItemType.INFIRE;
    if ((pos[numberOfBomb * 2 - 2] - Constants.offsetLeft) == 0) {
      left = false;
    }
    if ((pos[numberOfBomb * 2 - 2] - Constants.offsetLeft) == Constants.sizeOfBlocks
        * (Constants.BlocksInHorizontal - 1)) {
      right = false;
    }
    if ((pos[numberOfBomb * 2 - 1] - Constants.offsetUp) == 0) {
      hier = false;
    }
    if ((pos[numberOfBomb * 2 - 1] - Constants.offsetUp) == Constants.sizeOfBlocks
        * (Constants.BlocksInVertical - 1)) {
      low = false;
    }

    for (GameItem platform : Main.platforms) {
      if (platform.type == GameItem.ItemType.ULTIMATEBOX
          || platform.type == GameItem.ItemType.WOODBOX) {
        if (this.getTranslateX() - Constants.sizeOfBlocks == platform.getTranslateX()
            && this.getTranslateY() == platform.getTranslateY() && left == true) {
          if (platform.type == GameItem.ItemType.ULTIMATEBOX) {
            left = false;
          }
          if (platform.type == GameItem.ItemType.WOODBOX) {
            platform.destroy();
          }
        } else if (this.getTranslateX() + Constants.sizeOfBlocks == platform.getTranslateX()
            && this.getTranslateY() == platform.getTranslateY() && right == true) {
          if (platform.type == GameItem.ItemType.ULTIMATEBOX) {
            right = false;
          }
          if (platform.type == GameItem.ItemType.WOODBOX) {
            platform.destroy();
          }
        } else if (this.getTranslateX() == platform.getTranslateX()
            && this.getTranslateY() - Constants.sizeOfBlocks == platform.getTranslateY()
            && hier == true) {
          if (platform.type == GameItem.ItemType.ULTIMATEBOX) {
            hier = false;
          }
          if (platform.type == GameItem.ItemType.WOODBOX) {
            platform.destroy();
          }
        } else if (this.getTranslateX() == platform.getTranslateX()
            && this.getTranslateY() + Constants.sizeOfBlocks == platform.getTranslateY()
            && low == true) {
          if (platform.type == GameItem.ItemType.ULTIMATEBOX) {
            low = false;
          }
          if (platform.type == GameItem.ItemType.WOODBOX) {
            platform.destroy();
          }
        }
      }
    }

    if (left == true) {
      leftAnimation.play();
      leftView.setVisible(true);
      Main.platforms
          .get((int) ((((pos[numberOfBomb * 2 - 1] - Constants.offsetUp) / Constants.sizeOfBlocks)
              * Constants.BlocksInHorizontal)
              + ((pos[numberOfBomb * 2 - 2] - Constants.sizeOfBlocks - Constants.offsetLeft)
                  / Constants.sizeOfBlocks))).type = GameItem.ItemType.INFIRE;
      Main.player.checkPosition(pos[numberOfBomb * 2 - 2] - Constants.sizeOfBlocks,
          pos[numberOfBomb * 2 - 1]);
    }
    if (right == true) {
      rightAnimation.play();
      rightView.setVisible(true);
      Main.platforms
          .get((int) ((((pos[numberOfBomb * 2 - 1] - Constants.offsetUp) / Constants.sizeOfBlocks)
              * Constants.BlocksInHorizontal)
              + ((pos[numberOfBomb * 2 - 2] + Constants.sizeOfBlocks - Constants.offsetLeft)
                  / Constants.sizeOfBlocks))).type = GameItem.ItemType.INFIRE;
      Main.player.checkPosition(pos[numberOfBomb * 2 - 2] + Constants.sizeOfBlocks,
          pos[numberOfBomb * 2 - 1]);
    }
    if (hier == true) {
      hierAnimation.play();
      hierView.setVisible(true);
      Main.platforms
          .get((int) ((((pos[numberOfBomb * 2 - 1] - Constants.sizeOfBlocks - Constants.offsetUp)
              / Constants.sizeOfBlocks) * Constants.BlocksInHorizontal)
              + ((pos[numberOfBomb * 2 - 2] - Constants.offsetLeft)
                  / Constants.sizeOfBlocks))).type = GameItem.ItemType.INFIRE;
      Main.player.checkPosition(pos[numberOfBomb * 2 - 2],
          pos[numberOfBomb * 2 - 1] - Constants.sizeOfBlocks);
    }
    if (low == true) {
      lowAnimation.play();
      lowView.setVisible(true);
      Main.platforms
          .get((int) ((((pos[numberOfBomb * 2 - 1] + Constants.sizeOfBlocks - Constants.offsetUp)
              / Constants.sizeOfBlocks) * Constants.BlocksInHorizontal)
              + ((pos[numberOfBomb * 2 - 2] - Constants.offsetLeft)
                  / Constants.sizeOfBlocks))).type = GameItem.ItemType.INFIRE;
      Main.player.checkPosition(pos[numberOfBomb * 2 - 2],
          pos[numberOfBomb * 2 - 1] + Constants.sizeOfBlocks);
      Main.player.checkPosition(pos[numberOfBomb * 2 - 2], pos[numberOfBomb * 2 - 1]);
    }
  }

  /**
   * Method ends detonation
   * 
   * @see Bomb#endOfDetonation()
   */
  public void endOfDetonation() {
    Main.bomb[numberOfBomb - 1].setVisible(false);
    Main.bomb[numberOfBomb - 1].setTranslateX(-Constants.sizeOfBlocks);
    Main.bomb[numberOfBomb - 1].setTranslateY(-Constants.sizeOfBlocks);

    Main.platforms
        .get((int) ((((pos[numberOfBomb * 2 - 1] - Constants.offsetUp) / Constants.sizeOfBlocks)
            * Constants.BlocksInHorizontal)
            + ((pos[numberOfBomb * 2 - 2] - Constants.offsetLeft) / Constants.sizeOfBlocks))).type =
                GameItem.ItemType.PLATFORM;

    if (left == true) {
      leftAnimation.stop();
      leftView.setVisible(false);
      Main.platforms
          .get((int) ((((pos[numberOfBomb * 2 - 1] - Constants.offsetUp) / Constants.sizeOfBlocks)
              * Constants.BlocksInHorizontal)
              + ((pos[numberOfBomb * 2 - 2] - Constants.sizeOfBlocks - Constants.offsetLeft)
                  / Constants.sizeOfBlocks))).type = GameItem.ItemType.PLATFORM;
    }
    if (right == true) {
      rightAnimation.stop();
      rightView.setVisible(false);
      Main.platforms
          .get((int) ((((pos[numberOfBomb * 2 - 1] - Constants.offsetUp) / Constants.sizeOfBlocks)
              * Constants.BlocksInHorizontal)
              + ((pos[numberOfBomb * 2 - 2] + Constants.sizeOfBlocks - Constants.offsetLeft)
                  / Constants.sizeOfBlocks))).type = GameItem.ItemType.PLATFORM;
    }
    if (hier == true) {
      hierAnimation.stop();
      hierView.setVisible(false);
      Main.platforms
          .get((int) ((((pos[numberOfBomb * 2 - 1] - Constants.sizeOfBlocks - Constants.offsetUp)
              / Constants.sizeOfBlocks) * Constants.BlocksInHorizontal)
              + ((pos[numberOfBomb * 2 - 2] - Constants.offsetLeft)
                  / Constants.sizeOfBlocks))).type = GameItem.ItemType.PLATFORM;
    }
    if (low == true) {
      lowAnimation.stop();
      lowView.setVisible(false);
      Main.platforms
          .get((int) ((((pos[numberOfBomb * 2 - 1] + Constants.sizeOfBlocks - Constants.offsetUp)
              / Constants.sizeOfBlocks) * Constants.BlocksInHorizontal)
              + ((pos[numberOfBomb * 2 - 2] - Constants.offsetLeft)
                  / Constants.sizeOfBlocks))).type = GameItem.ItemType.PLATFORM;
    }

    left = right = hier = low = true;

    pos[numberOfBomb * 2 - 2] = -1;
    pos[numberOfBomb * 2 - 1] = -1;
    ready = true;
  }

}
