package com.alex.bsuir;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Class extends Transition and play animation
 */
public class MySpriteAnimation extends Transition {

  private final ImageView imageView;
  private final int count;
  private final int columns;
  private int offsetX;
  private int offsetY;
  private final int width;
  private final int height;

  private int type = 0;
  private int numberOfBomb;

  private int lastIndex;

  /**
   * Method set number of bomb
   * 
   * @param temp number of bomb
   * @see MySpriteAnimation#SetNumberOfBomb(int)
   */
  public void SetNumberOfBomb(int temp) {
    numberOfBomb = temp;
  }

  /**
   * Method set type of animation
   * 
   * @param temp type of animation
   * @see MySpriteAnimation#SetType(int)
   */
  public void SetType(int temp) {
    type = temp;
  }

  /**
   * @param imageView,duration,count,columns,offsetX,offsetY,width,height ImageView, duration of
   *        animation, number of frames, number of columns, offset by x, offset by y, size of frames
   * @see MySpriteAnimation#MySpriteAnimation(ImageView, Duration, int, int, int, int, int, int)
   */
  public MySpriteAnimation(ImageView imageView, Duration duration, int count, int columns,
      int offsetX, int offsetY, int width, int height) {
    this.imageView = imageView;
    this.count = count;
    this.columns = columns;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.width = width;
    this.height = height;
    setCycleDuration(duration);
    setInterpolator(Interpolator.LINEAR);
  }


  @Override
  /**
   * Method changes frames in animation
   * 
   * @param k relative position
   */
  protected void interpolate(double k) {
    final int index = Math.min((int) Math.floor(k * (count - 1)), count - 2);
    if (index != lastIndex) {
      final int x = (index % columns) * width + offsetX;
      final int y = (index / columns) * height + offsetY;
      imageView.setViewport(new Rectangle2D(x, y, width, height));
      lastIndex = index;
    }
    if (type == 1 && k == 1 && Main.start == true)
      Main.bomb[numberOfBomb - 1].detonation();
    if (type == 2 && k == 1 && Main.start == true)
      Main.bomb[numberOfBomb - 1].endOfDetonation();
  }

  /**
   * Method set end of animation
   * 
   * @see MySpriteAnimation#EndOfAnimation()
   */
  public void EndOfAnimation() {
    imageView.setViewport(new Rectangle2D(0, offsetY, width, height));
    this.stop();
  }

}
