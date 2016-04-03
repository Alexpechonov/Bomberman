package com.alex.bsuir;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Class extends StackPane and create menu
 */
public class MenuBox extends StackPane {
  public boolean isActive;

  public MenuBox(MenuItem... items) {
    Rectangle main = new Rectangle(Constants.menuWidth, Constants.menuHeight);
    main.setOpacity(0.2);

    DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
    shadow.setSpread(0.8);

    main.setEffect(shadow);

    Line vSep = new Line();
    vSep.setStartX(Constants.menuWidth);
    vSep.setEndX(Constants.menuWidth);
    vSep.setEndY(Constants.menuHeight);
    vSep.setStroke(Color.DARKRED);
    vSep.setOpacity(0.4);

    VBox vbox = new VBox();
    vbox.setAlignment(Pos.TOP_RIGHT);
    vbox.setPadding(new Insets(85, 0, 0, 0));
    vbox.getChildren().addAll(items);

    setAlignment(Pos.TOP_RIGHT);
    getChildren().addAll(main, vSep, vbox);
  }

  /**
   * Method show menu
   * 
   * @see MenuBox#show()
   */
  public void show() {
    setVisible(true);
    TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), this);
    trans.setToX(0);
    trans.play();
  }

  /**
   * Method hide menu
   * 
   * @see MenuBox#hide()
   */
  public void hide() {
    TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), this);
    trans.setToX(-Constants.menuWidth);
    trans.setOnFinished(event -> setVisible(false));
    trans.play();

  }

}
