package com.alex.bsuir;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class extends StackPane and create menu item
 */
public class MenuItem extends StackPane {

  public static final int RESUME_GAME = 0;
  public static final int NEW_GAME = 1;
  public static final int QUIT = 2;
  public static final int LEVEL1 = 3;
  public static final int LEVEL2 = 4;
  public static final int LEVEL3 = 5;
  public static final int SHOW_SAVES = 6;
  public static final int PLAY_SAVES = 7;
  public static final int BACK = 8;
  public static final int SCALA_SORT = 9;
  public static final int JAVA_SORT = 10;
  public static final int GENERATOR = 11;

  public MenuItem(String name, int type) {

    Rectangle menuRect = new Rectangle(Constants.menuWidth, Constants.itemHeight);

    LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
        new Stop[] {new Stop(0, Color.BLACK), new Stop(0.2, Color.YELLOW)});

    LinearGradient gradientPressed = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
        new Stop[] {new Stop(0, Color.BLACK), new Stop(0.5, Color.RED)});

    menuRect.setFill(gradient);
    menuRect.setVisible(false);
    menuRect.setEffect(new DropShadow(5, 0, 5, Color.BLACK));

    Text text = new Text(name + "         ");
    text.setFill(Color.YELLOW);
    text.setFont(Font.font(20));

    setAlignment(Pos.CENTER_RIGHT);
    getChildren().addAll(menuRect, text);

    setOnMouseEntered(event -> {
      menuRect.setVisible(true);
      text.setFill(Color.RED);
    });

    setOnMouseExited(event -> {
      menuRect.setVisible(false);
      text.setFill(Color.YELLOW);
    });

    setOnMousePressed(event -> {
      menuRect.setFill(gradientPressed);
      text.setFill(Color.YELLOW);
    });

    setOnMouseReleased(event -> {
      menuRect.setFill(gradient);
      text.setFill(Color.RED);
      if (type == QUIT) {
        System.exit(0);
      }
      if (type == NEW_GAME) {
        Main.menu.setVisible(false);
        Main.menu.isActive = false;
        Main.menuLevels.setVisible(true);
        Main.menuLevels.isActive = true;
      }
      if (type > 2 && type < 6) {
        Constants.save.openBufferForWrite();
        Constants.save.addLevelNumber(type - 3);
        Main.createGame(type - 3);
      }
      if (type == BACK) {
        Main.menu.setVisible(true);
        Main.menu.isActive = true;
        Main.menuLevels.setVisible(false);
        Main.menuLevels.isActive = false;
      }
      if (type == PLAY_SAVES) {
        Constants.save.openBufferForRead();
        Main.activeReplay = true;
        Main.createGame(Constants.save.getIntFromFile());
      }
      if (type == SHOW_SAVES) {
        Constants.save.showSaves();
        GameInfo.analizeInfo();
      }
      if (type == SCALA_SORT) {
        Constants.save.convertSavesInToInfo();
        Constants.save.sortInfoList();
      }
      if (type == JAVA_SORT) {
        Constants.save.convertSavesInToInfo();
        Constants.save.javaSortInfoList();
      }
      if (type == GENERATOR) {
        NotationGenerator generator = new NotationGenerator();
        generator.generateSaves();
      }
    });

  }
}

