package com.alex.bsuir;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Class extends Pane and describes the logic of player
 */
public class Character extends Pane {
  int speedX;
  int speedY;

  private boolean ready = true;
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

    if ((int) (Math.random() * 2) == 0)
      speedX = Constants.speedOfBomberman;
    else
      speedY = Constants.speedOfBomberman;

    alive = true;
    imageView.setFitHeight(Constants.sizeOfCharacter);
    imageView.setFitWidth(Constants.sizeOfCharacter);
    imageView.setViewport(new Rectangle2D(offsetX - 18, offsetY + 20, width, height));
    animationRaL = new MySpriteAnimation(this.imageView, Duration.millis(350), count, columns,
        offsetX, offsetY, width, height);
    animationUp = new MySpriteAnimation(this.imageView, Duration.millis(350), count, columns,
        offsetX, offsetY + 40, width, height);
    animationDown = new MySpriteAnimation(this.imageView, Duration.millis(350), count, columns,
        offsetX, offsetY + 20, width, height);
    deathAnimation =
        new MySpriteAnimation(this.imageView, Duration.millis(2000), 10, 9, 0, 60, 23, height);
    getChildren().addAll(this.imageView);
  }

  /**
   * Method return state of character
   * 
   * @return boolean isAlive
   * @see Character#isAlive()
   */
  public boolean isAlive() {
    return alive;
  }

  /**
   * Method move Character by x
   * 
   * @param value count of positions to move
   * @see Character#moveX(int)
   */
  public void moveX(int value) {
    boolean movingRight = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
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
    }
  }

  /**
   * Method move Character by y
   * 
   * @param value count of positions to move
   * @see Character#moveY(int)
   */
  public void moveY(int value) {
    boolean movingDown = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
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
    }
  }

  /**
   * Method check position
   * 
   * @param x,y position of bock by x and y
   * @see Character#checkPosition(int, int)
   */
  public void checkPosition(int x, int y) {
    if (this.getBoundsInParent().intersects(x, y, Constants.sizeOfBlocks, Constants.sizeOfBlocks))
      setDeath();
  }

  /**
   * Method for kill the character
   * 
   * @see Character#setDeath()
   */
  public void setDeath() {
    if (alive == true) {
      System.out.print(alive);
      Main.start = false;
      alive = false;
      deathAnimation.play();
      Main.createEndOfGame();

    }
  }

  /**
   * Method check rigt position near character
   * 
   * @param platform block
   * @see Character#checkRight(GameItem)
   * @return boolean true if speed was changed
   */
  private boolean checkRight(GameItem platform) {
    if ((this.getTranslateX() + Constants.sizeOfCharacter == platform.getTranslateX())
        && (platform.type != GameItem.ItemType.PLATFORM)
        && (platform.type != GameItem.ItemType.INFIRE)) {
      this.setTranslateX(this.getTranslateX() - 1);
      speedX = -speedX;
      if (this.getTranslateY() + Constants.sizeOfCharacter - 5 <= platform.getTranslateY())
        this.setTranslateY(this.getTranslateY() - 1);
      if (this.getTranslateY() >= platform.getTranslateY() + Constants.sizeOfCharacter)
        this.setTranslateY(this.getTranslateY() + 1);
      return false;
    }
    return true;
  }

  /**
   * Method check left position near character
   * 
   * @param platform block
   * @see Character#checkLeft(GameItem)
   * @return boolean true if speed was changed
   */
  private boolean checkLeft(GameItem platform) {
    if ((this.getTranslateX() == platform.getTranslateX() + Constants.sizeOfBlocks)
        && (platform.type != GameItem.ItemType.PLATFORM)
        && (platform.type != GameItem.ItemType.INFIRE)) {
      this.setTranslateX(this.getTranslateX() + 1);
      speedX = -speedX;
      if (this.getTranslateY() + Constants.sizeOfCharacter - 5 <= platform.getTranslateY())
        this.setTranslateY(this.getTranslateY() - 1);
      if (this.getTranslateY() >= platform.getTranslateY() + Constants.sizeOfCharacter)
        this.setTranslateY(this.getTranslateY() + 1);
      return false;
    }
    return true;
  }

  /**
   * Method check down position near character
   * 
   * @param platform block
   * @see Character#checkDown(GameItem)
   * @return boolean true if speed was changed
   */
  private boolean checkDown(GameItem platform) {
    if ((this.getTranslateY() + Constants.sizeOfCharacter == platform.getTranslateY())
        && (platform.type != GameItem.ItemType.PLATFORM)
        && (platform.type != GameItem.ItemType.INFIRE)) {
      this.setTranslateY(this.getTranslateY() - 1);
      speedY = -speedY;
      if (this.getTranslateX() >= platform.getTranslateX() + Constants.sizeOfBlocks - 5)
        this.setTranslateX(this.getTranslateX() + 1);
      if (this.getTranslateX() + Constants.sizeOfCharacter - 5 <= platform.getTranslateX())
        this.setTranslateX(this.getTranslateX() - 1);
      return false;
    }
    return true;
  }

  /**
   * Method check upper position near character
   * 
   * @param platform block
   * @see Character#checkUp(GameItem)
   * @return boolean true if speed was changed
   */
  private boolean checkUp(GameItem platform) {
    if ((this.getTranslateY() == platform.getTranslateY() + Constants.sizeOfBlocks)
        && (platform.type != GameItem.ItemType.PLATFORM)
        && (platform.type != GameItem.ItemType.INFIRE)) {
      this.setTranslateY(this.getTranslateY() + 1);
      speedY = -speedY;
      if (this.getTranslateX() >= platform.getTranslateX() + Constants.sizeOfBlocks - 5)
        this.setTranslateX(this.getTranslateX() + 1);
      if (this.getTranslateX() + Constants.sizeOfCharacter - 5 <= platform.getTranslateX())
        this.setTranslateX(this.getTranslateX() - 1);
      return false;
    }
    return true;
  }

  /**
   * Method move Character by x when we use Artificial Intelligence
   * 
   * @param value count of positions to move
   * @see Character#autoMoveX(int)
   */
  public void autoMoveX(int value) {
    boolean movingRight = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
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
      if ((this.getTranslateX() - Constants.offsetLeft - 3) % Constants.sizeOfBlocks == 0
          && (this.getTranslateY() - Constants.offsetUp - 3) % Constants.sizeOfBlocks == 0) {
        switch ((int) (Math.random() * 6)) {
          case 1:
            if (this.getTranslateY() > Constants.offsetUp + 3) {
              if (Main.platforms.get(
                  (int) ((((this.getTranslateY() - 3 - Constants.sizeOfBlocks - Constants.offsetUp)
                      / Constants.sizeOfBlocks) * Constants.BlocksInHorizontal)
                      + ((this.getTranslateX() - 3 - Constants.offsetLeft)
                          / Constants.sizeOfBlocks))).type == GameItem.ItemType.PLATFORM) {
                speedY = -Math.abs(speedX);
                speedX = 0;
                return;
              }
            }
          case 2:
            if (this.getTranslateY() < Constants.offsetUp
                + (Constants.BlocksInVertical - 1) * Constants.sizeOfBlocks + 3) {
              if (Main.platforms.get(
                  (int) ((((this.getTranslateY() - 3 + Constants.sizeOfBlocks - Constants.offsetUp)
                      / Constants.sizeOfBlocks) * Constants.BlocksInHorizontal)
                      + ((this.getTranslateX() - 3 - Constants.offsetLeft)
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
   * Method move Character by y when we use Artificial Intelligence
   * 
   * @param value count of positions to move
   * @see Character#autoMoveY(int)
   */
  public void autoMoveY(int value) {
    boolean movingDown = value > 0;
    for (int i = 0; i < Math.abs(value); i++) {
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
      if ((this.getTranslateX() - Constants.offsetLeft - 3) % Constants.sizeOfBlocks == 0
          && (this.getTranslateY() - Constants.offsetUp - 3) % Constants.sizeOfBlocks == 0) {
        switch ((int) (Math.random() * 6)) {
          case 1:
            if (this.getTranslateX() > Constants.offsetLeft + 3) {
              if (Main.platforms.get(
                  (int) ((((this.getTranslateY() - 3 - Constants.offsetUp) / Constants.sizeOfBlocks)
                      * Constants.BlocksInHorizontal)
                      + ((this.getTranslateX() - 3 - Constants.sizeOfBlocks - Constants.offsetLeft)
                          / Constants.sizeOfBlocks))).type == GameItem.ItemType.PLATFORM) {
                speedX = -Math.abs(speedY);
                speedY = 0;
                return;
              }
            }
          case 2:
            if (this.getTranslateX() < Constants.offsetLeft
                + (Constants.BlocksInHorizontal - 1) * Constants.sizeOfBlocks + 3) {
              if (Main.platforms.get(
                  (int) ((((this.getTranslateY() - 3 - Constants.offsetUp) / Constants.sizeOfBlocks)
                      * Constants.BlocksInHorizontal)
                      + ((this.getTranslateX() - 3 + Constants.sizeOfBlocks - Constants.offsetLeft)
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
   * Method move Character when we use Artificial Intelligence
   * 
   * @see Character#autoMove()
   */
  public void autoMove() {
    if (ready == true) {
      ready = false;
      if (this.getTranslateX() + Constants.sizeOfEnemy >= Constants.screenWidth
          - Constants.offsetLeft || this.getTranslateX() <= Constants.offsetLeft)
        speedX = -speedX;
      if (this.getTranslateY() + Constants.sizeOfEnemy >= Constants.offsetUp
          + Constants.BlocksInVertical * Constants.sizeOfBlocks
          || this.getTranslateY() <= Constants.offsetUp)
        speedY = -speedY;
      if (speedX > 0) {
        this.setScaleX(1);
        this.animationRaL.play();
      } else if (speedX < 0) {
        this.setScaleX(-1);
        this.animationRaL.play();
      } else if (speedY > 0) {
        this.animationDown.play();
      } else if (speedY < 0) {
        this.animationUp.play();
      }

      if (speedX != 0)
        autoMoveX(speedX);
      if (speedY != 0)
        autoMoveY(speedY);
      ready = true;
    }
  }

  /**
   * Method normalize character's position
   * 
   * @see Character#normalize()
   */
  public void normalize() {
    double posX = this.getTranslateX() + Constants.sizeOfCharacter / 2 - Constants.offsetLeft;
    double posY = this.getTranslateY() + Constants.sizeOfCharacter / 2 - Constants.offsetUp;
    posX = posX - (posX % Constants.sizeOfBlocks) + Constants.offsetLeft + 3;
    posY = posY - (posY % Constants.sizeOfBlocks) + Constants.offsetUp + 3;

    while (Math.abs(posX - this.getTranslateX()) > Constants.speedOfBomberman) {
      if (posX - this.getTranslateX() > 0)
        moveX(1);
      else
        moveX(-1);
    }
    moveX((int) (posX - this.getTranslateX()));

    while (Math.abs(posY - this.getTranslateY()) > Constants.speedOfBomberman) {
      if (posY - this.getTranslateY() > 0)
        moveY(Constants.speedOfBomberman);
      else
        moveY(-Constants.speedOfBomberman);
    }
    moveY((int) (posY - this.getTranslateY()));

  }

}


