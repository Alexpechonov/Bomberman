package com.alex.bsuir;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Class extends Pane and describes the logic of enemies
 */
public class Enemy extends Pane {
  Image enemyImage =
      new Image(getClass().getResourceAsStream("res/sprites/bomberman_enemies_sheet.png"));
  ImageView imageView = new ImageView(enemyImage);
  MySpriteAnimation animation, deathAnimation;

  /**
   * Variable shows if enemy is alive
   */
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
    imageView.setFitHeight(Constants.sizeOfEnemy);
    imageView.setFitWidth(Constants.sizeOfEnemy);
    imageView.setViewport(new Rectangle2D(0, 0, 16, 16));
    animation = new MySpriteAnimation(imageView, Duration.millis(1000), 7, 6, 0, 0, 16, 16);
    deathAnimation = new MySpriteAnimation(imageView, Duration.millis(1500), 7, 6, 0, 16, 16, 16);
    getChildren().addAll(this.imageView);
  }

  /**
   * Method move Enemy by x
   * 
   * @param value count of positions to move
   * @see Enemy#moveX(int)
   */
  public void moveX(int value) {
    boolean movingRight = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
      if (this.getBoundsInParent().intersects(Main.player.getBoundsInParent()))
        Main.player.setDeath();
      for (GameItem platform : Main.platforms) {
        if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
          if (platform.type == GameItem.ItemType.INFIRE) {
            setDeath();
            return;
          }
          if (movingRight) {
            if (checkRight(platform) == false)
              return;
          } else {
            if (checkLeft(platform) == false)
              return;
          }
        }
      }
      this.setTranslateX(this.getTranslateX() + (movingRight ? 1 : -1));
      if ((this.getTranslateX() - Constants.offsetLeft - 1) % Constants.sizeOfBlocks == 0
          && (this.getTranslateY() - Constants.offsetUp - 1) % Constants.sizeOfBlocks == 0) {
        switch ((int) (Math.random() * 6)) {
          case 1:
            if (this.getTranslateY() > Constants.offsetUp + 1) {
              if (Main.platforms.get(
                  (int) ((((this.getTranslateY() - 1 - Constants.sizeOfBlocks - Constants.offsetUp)
                      / Constants.sizeOfBlocks) * Constants.BlocksInHorizontal)
                      + ((this.getTranslateX() - 1 - Constants.offsetLeft)
                          / Constants.sizeOfBlocks))).type == GameItem.ItemType.PLATFORM) {
                speedY = -Math.abs(speedX);
                speedX = 0;
                return;
              }
            }
          case 2:
            if (this.getTranslateY() < Constants.offsetUp
                + (Constants.BlocksInVertical - 1) * Constants.sizeOfBlocks + 1) {
              if (Main.platforms.get(
                  (int) ((((this.getTranslateY() - 1 + Constants.sizeOfBlocks - Constants.offsetUp)
                      / Constants.sizeOfBlocks) * Constants.BlocksInHorizontal)
                      + ((this.getTranslateX() - 1 - Constants.offsetLeft)
                          / Constants.sizeOfBlocks))).type == GameItem.ItemType.PLATFORM) {
                speedY = Math.abs(speedX);
                speedX = 0;
                return;
              }
            }
        }

      }
    }
  }

  /**
   * Method move Enemy by y
   * 
   * @param value count of positions to move
   * @see Enemy#moveY(int)
   */
  public void moveY(int value) {
    boolean movingDown = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
      if (this.getBoundsInParent().intersects(Main.player.getBoundsInParent()))
        Main.player.setDeath();
      for (GameItem platform : Main.platforms) {
        if (this.getBoundsInParent().intersects(platform.getBoundsInParent())) {
          if (platform.type == GameItem.ItemType.INFIRE) {
            setDeath();
            return;
          }
          if (movingDown) {
            if (checkDown(platform) == false)
              return;
          } else {
            if (checkUp(platform) == false)
              return;
          }
        }
      }
      this.setTranslateY(this.getTranslateY() + (movingDown ? 1 : -1));
      if ((this.getTranslateX() - Constants.offsetLeft - 1) % Constants.sizeOfBlocks == 0
          && (this.getTranslateY() - Constants.offsetUp - 1) % Constants.sizeOfBlocks == 0) {
        switch ((int) (Math.random() * 6)) {
          case 1:
            if (this.getTranslateX() > Constants.offsetLeft + 1) {
              if (Main.platforms.get(
                  (int) ((((this.getTranslateY() - 1 - Constants.offsetUp) / Constants.sizeOfBlocks)
                      * Constants.BlocksInHorizontal)
                      + ((this.getTranslateX() - 1 - Constants.sizeOfBlocks - Constants.offsetLeft)
                          / Constants.sizeOfBlocks))).type == GameItem.ItemType.PLATFORM) {
                speedX = -Math.abs(speedY);
                speedY = 0;
                return;
              }
            }
          case 2:
            if (this.getTranslateX() < Constants.offsetLeft
                + (Constants.BlocksInHorizontal - 1) * Constants.sizeOfBlocks + 1) {
              if (Main.platforms.get(
                  (int) ((((this.getTranslateY() - 1 - Constants.offsetUp) / Constants.sizeOfBlocks)
                      * Constants.BlocksInHorizontal)
                      + ((this.getTranslateX() - 1 + Constants.sizeOfBlocks - Constants.offsetLeft)
                          / Constants.sizeOfBlocks))).type == GameItem.ItemType.PLATFORM) {
                speedX = Math.abs(speedY);
                speedY = 0;
                return;
              }
            }
        }

      }
    }
  }

  /**
   * Method for kill the enemy
   * 
   * @see Enemy#setDeath()
   */
  private void setDeath() {
    animation.stop();
    isAlive = false;
    deathAnimation.play();
  }

  /**
   * Method return state of enemy
   * 
   * @return boolean isAlive
   * @see Enemy#getAlive()
   */
  public boolean getAlive() {
    return isAlive;
  }

  /**
   * Method move Enemy
   * 
   * @see Enemy#move()
   */
  public void move() {
    if (ready == true) {
      ready = false;
      if (this.getTranslateX() + Constants.sizeOfEnemy >= maxX || this.getTranslateX() <= minX)
        speedX = -speedX;
      if (this.getTranslateY() + Constants.sizeOfEnemy >= maxY || this.getTranslateY() <= minY)
        speedY = -speedY;
      animation.play();
      if (speedX != 0)
        moveX(speedX);
      if (speedY != 0)
        moveY(speedY);
      ready = true;
    }

  }

  /**
   * Method set speed of enemy
   * 
   * @param x,y speed by x and speed by y
   * @see Enemy#setSpeed(int, int)
   */
  public void setSpeed(int x, int y) {
    speedX = x;
    speedY = y;
  }

  /**
   * Method set area where enemy will be move
   * 
   * @param x1,x2,y1,y2 min and max position by x, min and max position by y
   * @see Enemy#setArea(int, int, int, int)
   */
  public void setArea(int x1, int x2, int y1, int y2) {
    minX = x1;
    maxX = x2;
    minY = y1;
    maxY = y2;
  }

  /**
   * Method check rigt position near enemy
   * 
   * @param platform block
   * @see Enemy#checkRight(GameItem)
   * @return boolean true if speed was changed
   */
  private boolean checkRight(GameItem platform) {
    if ((this.getTranslateX() + Constants.sizeOfEnemy == platform.getTranslateX())
        && (platform.type != GameItem.ItemType.PLATFORM)
        && (platform.type != GameItem.ItemType.INFIRE)) {
      this.setTranslateX(this.getTranslateX() - 1);
      speedX = -speedX;
      return false;
    }
    return true;
  }

  /**
   * Method check left position near enemy
   * 
   * @param platform block
   * @see Enemy#checkLeft(GameItem)
   * @return boolean true if speed was changed
   */
  private boolean checkLeft(GameItem platform) {
    if ((this.getTranslateX() == platform.getTranslateX() + Constants.sizeOfBlocks)
        && (platform.type != GameItem.ItemType.PLATFORM)
        && (platform.type != GameItem.ItemType.INFIRE)) {
      this.setTranslateX(this.getTranslateX() + 1);
      speedX = -speedX;
      return false;
    }
    return true;
  }

  /**
   * Method check down position near enemy
   * 
   * @param platform block
   * @see Enemy#checkDown(GameItem)
   * @return boolean true if speed was changed
   */
  private boolean checkDown(GameItem platform) {
    if ((this.getTranslateY() + Constants.sizeOfEnemy == platform.getTranslateY())
        && (platform.type != GameItem.ItemType.PLATFORM)
        && (platform.type != GameItem.ItemType.INFIRE)) {
      this.setTranslateY(this.getTranslateY() - 1);
      speedY = -speedY;
      return false;
    }
    return true;
  }

  /**
   * Method check upper position near enemy
   * 
   * @param platform block
   * @see Enemy#checkUp(GameItem)
   * @return boolean true if speed was changed
   */
  private boolean checkUp(GameItem platform) {
    if ((this.getTranslateY() == platform.getTranslateY() + Constants.sizeOfBlocks)
        && (platform.type != GameItem.ItemType.PLATFORM)
        && (platform.type != GameItem.ItemType.INFIRE)) {
      this.setTranslateY(this.getTranslateY() + 1);
      speedY = -speedY;
      return false;
    }
    return true;
  }
}
