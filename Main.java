package com.alex.bsuir;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Class extends Application and make main game window
 * 
 * @author lexa19971997@tut.by
 * @version 1.0
 */
public class Main extends Application {

  /**
   * Variable shows if game was started
   */
  static boolean start = false;
  /**
   * Variable shows if artificial intelligence is active
   */
  public static boolean activeAI = false;

  private static Scene main_scene;
  private static Stage endOfGame;
  private static Scene endScene;

  public static MenuBox menu;
  public static MenuBox menuLevels;

  /**
   * Variable contains platforms of game field
   */
  public static ArrayList<GameItem> platforms = new ArrayList<>();
  /**
   * Variable contains keys codes
   */
  private static HashMap<KeyCode, Boolean> keys = new HashMap<>();

  public static Pane mainRoot = new Pane();
  public static Pane gameRoot;

  public static Character player;
  public static Bomb[] bomb;
  public static Enemy[] enemies;
  /**
   * Variable contains count of active enemies
   */
  public static int countOfEnemies = 0;

  /**
   * Method create settings of main menu
   * 
   * @see Main#createMenuSettings()
   */
  private static void createMenuSettings() {
    main_scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ESCAPE) {
        if (menu.isVisible() || menuLevels.isVisible()) {
          if (menu.isActive == true) {
            menu.hide();
          } else {
            menuLevels.hide();
          }
        } else {
          if (menu.isActive == true) {
            menu.show();
          } else {
            menuLevels.show();
          }
        }
      }
    });
  }

  /**
   * Method create settings of game field
   * 
   * @see Main#createGameSettings()
   */
  private static void createGameSettings() {
    main_scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
    main_scene.setOnKeyReleased(event -> {
      keys.put(event.getCode(), false);
      if (activeAI == false) {
        if ((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
            && player.isAlive() == true)
          player.animationRaL.EndOfAnimation();
        if (event.getCode() == KeyCode.DOWN && player.isAlive() == true)
          player.animationDown.EndOfAnimation();
        if (event.getCode() == KeyCode.UP && player.isAlive() == true)
          player.animationUp.EndOfAnimation();
      }
      if (event.getCode() == KeyCode.A && player.isAlive() == true) {
        if (activeAI == true) {
          activeAI = false;
          player.animationDown.stop();
          player.animationRaL.stop();
          player.animationUp.stop();
          player.animationRaL.EndOfAnimation();
        } else
          activeAI = true;
        player.normalize();
      }
    });

  }

  /**
   * Method create window after end of game
   * 
   * @see Main#createEndOfGame()
   */
  public static void createEndOfGame() {
    endOfGame = new Stage();
    endOfGame.setTitle("GAME OVER");
    keys.clear();
    createMenuSettings();

    Image background = new Image(Main.class.getResourceAsStream("res/images/GAME_OVER.jpg"));
    ImageView img = new ImageView(background);
    img.setFitWidth(300);
    img.setFitHeight(200);
    Pane endRoot = new Pane();
    endRoot.setPrefSize(300, 200);
    Button but = new Button();
    but.setText("RETURN TO MENU");
    but.setTextFill(Color.RED);
    but.setTranslateX(90);
    but.setTranslateY(160);
    but.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent event) {
        main_scene.setRoot(mainRoot);
        endOfGame.close();
        platforms.clear();
        countOfEnemies = 0;
        activeAI = false;
      }
    });
    endOfGame.setOnCloseRequest(new EventHandler<WindowEvent>() {
      public void handle(WindowEvent event) {
        main_scene.setRoot(mainRoot);
        endOfGame.close();
        platforms.clear();
        countOfEnemies = 0;
        activeAI = false;
      }
    });
    endRoot.getChildren().addAll(img, but);
    endScene = new Scene(endRoot);
    endOfGame.setScene(endScene);
    endOfGame.show();

  }

  /**
   * Method create gamefield
   * 
   * @param numberOfLevel number of level
   * @see Main#createPlayContent(int)
   */
  public static void createPlayContent(int numberOfLevel) {

    start = false;
    gameRoot = new Pane();

    gameRoot.setPrefSize(Constants.screenHeight, Constants.screenWidth);

    Image background = new Image(Main.class.getResourceAsStream("res/images/grass.jpg"));
    ImageView img = new ImageView(background);
    img.setFitWidth(Constants.screenWidth);
    img.setFitHeight(Constants.screenHeight);

    Image playSpace = new Image(Main.class.getResourceAsStream("res/images/sea.jpg"));
    ImageView image = new ImageView(playSpace);
    image.setFitWidth(Constants.BackPlayWidth);
    image.setFitHeight(Constants.BackPlayHeight);
    image.setTranslateX((Constants.screenWidth - Constants.BackPlayWidth) / 2);
    image.setTranslateY((Constants.screenHeight - Constants.BackPlayHeight) / 7 * 6);

    gameRoot.getChildren().addAll(img, image);

    for (int i = 0; i < 9; i++) {
      String line = LevelData.levels[numberOfLevel][i];
      for (int j = 0; j < line.length(); j++) {
        switch (line.charAt(j)) {
          case '0':
            GameItem platformFloor = new GameItem(GameItem.ItemType.PLATFORM,
                Constants.offsetLeft + j * Constants.sizeOfBlocks,
                Constants.offsetUp + i * Constants.sizeOfBlocks);
            break;
          case '1':
            GameItem woodItem = new GameItem(GameItem.ItemType.WOODBOX,
                Constants.offsetLeft + j * Constants.sizeOfBlocks,
                Constants.offsetUp + i * Constants.sizeOfBlocks);
            break;
          case '2':
            GameItem ultItem = new GameItem(GameItem.ItemType.ULTIMATEBOX,
                Constants.offsetLeft + j * Constants.sizeOfBlocks,
                Constants.offsetUp + i * Constants.sizeOfBlocks);
        }
      }
    }

    enemies = new Enemy[LevelData.levels[numberOfLevel].length - Constants.BlocksInVertical];

    for (int i =
        0; i < (LevelData.levels[numberOfLevel].length - Constants.BlocksInVertical); i++) {
      Enemy enemy = new Enemy();
      String line = LevelData.levels[numberOfLevel][Constants.BlocksInVertical + i];
      enemy.setArea(120 + Constants.sizeOfBlocks * myAtoi(line.charAt(2)),
          120 + Constants.sizeOfBlocks * myAtoi(line.charAt(3)),
          170 + Constants.sizeOfBlocks * myAtoi(line.charAt(4)),
          170 + Constants.sizeOfBlocks * myAtoi(line.charAt(5)));
      int speedX = -myAtoi(line.charAt(6));
      if (myAtoi(line.charAt(8)) == 0)
        speedX = -speedX;
      int speedY = -myAtoi(line.charAt(7));
      if (myAtoi(line.charAt(9)) == 0)
        speedY = -speedY;
      enemy.setSpeed(speedX, speedY);
      enemies[i] = enemy;
      enemies[i].setTranslateX(
          Constants.offsetLeft + Constants.sizeOfBlocks * myAtoi(line.charAt(0)) + 1);
      enemies[i]
          .setTranslateY(Constants.offsetUp + Constants.sizeOfBlocks * myAtoi(line.charAt(1)) + 1);
      countOfEnemies++;
    }

    player = new Character();
    player.setTranslateX(Constants.offsetLeft);
    player.setTranslateY(Constants.offsetUp);
    bomb = new Bomb[3];
    for (int i = 0; i < 3; i++) {
      Bomb bomb1 = new Bomb(i + 1);
      bomb[i] = bomb1;
      bomb[i].setVisible(false);
    }

    gameRoot.getChildren().add(player);
    gameRoot.getChildren().addAll(bomb);
    gameRoot.getChildren().addAll(enemies);

    createGameSettings();

    main_scene.setRoot(gameRoot);
  }

  /**
   * Method controle game action
   * 
   * @see Main#update()
   */
  private void update() {
    if (gameRoot != null) {
      if (player.isAlive() == true) {
        if (activeAI == false) {
          if (isPressed(KeyCode.RIGHT) && player.getTranslateX()
              + Constants.sizeOfCharacter < Constants.screenWidth - Constants.offsetLeft) {
            start = true;
            player.setScaleX(1);
            player.animationRaL.play();
            player.moveX(Constants.speedOfBomberman);
          } else if (isPressed(KeyCode.RIGHT)) {
            player.setScaleX(1);
            player.animationRaL.EndOfAnimation();
          }
          if (isPressed(KeyCode.LEFT) && player.getTranslateX() > Constants.offsetLeft) {
            player.setScaleX(-1);
            player.animationRaL.play();
            player.moveX(-Constants.speedOfBomberman);
          } else if (isPressed(KeyCode.LEFT)) {
            player.setScaleX(-1);
            player.animationRaL.EndOfAnimation();
          }
          if (isPressed(KeyCode.UP) && player.getTranslateY() > Constants.offsetUp) {
            player.animationUp.play();
            player.moveY(-Constants.speedOfBomberman);
          } else if (isPressed(KeyCode.UP)) {
            player.animationUp.EndOfAnimation();
          }
          if (isPressed(KeyCode.DOWN)
              && player.getTranslateY() + Constants.sizeOfCharacter < Constants.offsetUp
                  + Constants.BlocksInVertical * Constants.sizeOfBlocks) {
            start = true;
            player.animationDown.play();
            player.moveY(Constants.speedOfBomberman);
          } else if (isPressed(KeyCode.DOWN)) {
            player.animationDown.EndOfAnimation();
          }
        }
        if (isPressed(KeyCode.SPACE)) {
          start = true;
          for (int i = 0; i < 3; i++) {
            if (bomb[i].isReady()) {
              bomb[i].setBomb();
              break;
            }
          }
        }
        boolean win = true;
        if (start == true && player.isAlive()) {
          for (int i = 0; i < countOfEnemies; i++) {
            if (enemies[i].getAlive() == true) {
              enemies[i].move();
              win = false;
            }
          }
        }
        if (start == true && win == true) {
          start = false;
          Main.createEndOfGame();
        }
        if (activeAI == true) {
          start = true;
          player.autoMove();
        }
      }
    }
  }

  /**
   * Check if some button was pressed
   * 
   * @param key code of button
   * @return boolean description
   */
  private boolean isPressed(KeyCode key) {
    return keys.getOrDefault(key, false);
  }

  /**
   * Method create main menu
   * 
   * @see Main#createContent()
   * @return Pain mainRoot
   */
  private Parent createContent() {
    mainRoot.setPrefSize(Constants.screenWidth, Constants.screenHeight);

    Image im = new Image(this.getClass().getResourceAsStream("res/images/MainBomberman.jpg"));
    ImageView img = new ImageView(im);
    img.setFitWidth(1066);
    img.setFitHeight(600);

    mainRoot.getChildren().add(img);

    menu = new MenuBox(new MenuItem("RESUME GAME", MenuItem.RESUME_GAME),
        new MenuItem("NEW GAME", MenuItem.NEW_GAME), new MenuItem("QUIT", MenuItem.QUIT));
    menu.isActive = true;

    menuLevels = new MenuBox(new MenuItem("LEVEL 1", MenuItem.LEVEL1),
        new MenuItem("LEVEL 2", MenuItem.LEVEL2), new MenuItem("LEVEL 3", MenuItem.LEVEL3),
        new MenuItem("BACK", MenuItem.BACK));
    menuLevels.isActive = false;
    menuLevels.setVisible(false);
    mainRoot.getChildren().addAll(menuLevels, menu);

    return mainRoot;

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    main_scene = new Scene(createContent());
    createMenuSettings();
    primaryStage.setTitle("Bomberman");
    primaryStage.setMaxHeight(Constants.screenHeight);
    primaryStage.setMaxWidth(Constants.screenWidth);
    primaryStage.setScene(main_scene);
    primaryStage.show();

    AnimationTimer timer = new AnimationTimer() {

      @Override
      public void handle(long now) {
        update();

      }

    };
    timer.start();

  }

  /**
   * Method convert char to int
   * 
   * @param c symbol
   * @see Main#myAtoi(char)
   */
  private static int myAtoi(char c) {
    int preobr = 0;
    if (c >= '0' && c <= '9')
      preobr = c - '0';
    if (c >= 'A' && c <= 'J')
      preobr = c - 'A' + 10;
    return preobr;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
