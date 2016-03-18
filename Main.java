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
import javafx.stage.Stage;

public class Main extends Application {
	
	static boolean start = false;
	
	private static Scene main_scene;
	private static Stage endOfGame;
	private static Scene endScene;
	
	private static MenuBox menu;
	public static ArrayList<GameItem> platforms = new ArrayList<>();
	private static HashMap<KeyCode,Boolean> keys = new HashMap<>();
	
	public static Pane mainRoot = new Pane();
	public static Pane gameRoot;// = new Pane();
	
	public static Character player;// = new Character();
	public static Bomb[] bomb;
	public static Enemy[] enemies;
	public static int countOfEnemies = 0;
	
	
	private int speedOfBomberman = 2;
	
	public static void createEndOfGame() {
		endOfGame = new Stage();
		keys.clear();
		
		main_scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				if (menu.isVisible()) {
					menu.hide();
				}
				else {
					menu.show();
				}
			}
		});
		
		Pane endRoot = new Pane();
		endRoot.setPrefSize(300, 200);
		Button but = new Button();
		but.setText("RETURN TO MENU");
		but.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				main_scene.setRoot(mainRoot);
				endOfGame.close();
				platforms.clear();
				countOfEnemies = 0;		
			} });
		endRoot.getChildren().add(but);
		endScene = new Scene(endRoot);
		endOfGame.setScene(endScene);
		endOfGame.show();
	}
	
	public static void createPlayContent() {
		
		start = false;
		gameRoot = new Pane();
		
		gameRoot.setPrefSize(600, 1000);
		
		Image background = new Image(Main.class.getResourceAsStream("res/images/grass.jpg"));
		ImageView img = new ImageView(background);
		img.setFitWidth(1000);
		img.setFitHeight(600);
		
		Image playSpace = new Image(Main.class.getResourceAsStream("res/images/sea.jpg"));
		ImageView image = new ImageView(playSpace);
		image.setFitWidth(800);
		image.setFitHeight(425);
		image.setTranslateX(100);
		image.setTranslateY(150);
		
		gameRoot.getChildren().addAll(img, image);
		
		for(int i = 0; i < 9; i++) {
			//String line = LevelData.levels[0][i];
			String line = LevelData.LEVEL1[i];
			for(int j = 0; j < line.length();j++) {
				switch (line.charAt(j)) {
				case '0' :
					GameItem platformFloor = new GameItem(GameItem.ItemType.PLATFORM,120 + j * 40,170 + i * 40);
					break;
				case '1' :
					GameItem woodItem = new GameItem(GameItem.ItemType.WOODBOX,120 + j * 40,170 + i * 40);
					break;
				case '2' :
					GameItem ultItem = new GameItem(GameItem.ItemType.ULTIMATEBOX,120 + j * 40,170 + i * 40);
				}
			}
		}
		
		enemies = new Enemy[LevelData.levels[0].length - 9];
		
		for (int i = 0; i < (LevelData.levels[0].length - 9); i++) {
			Enemy enemy = new Enemy();
			String line = LevelData.levels[0][9 + i];
			enemy.setArea(120 + 40 * myAtoi(line.charAt(2)),120 + 40 * myAtoi(line.charAt(3)),170 + 40 * myAtoi(line.charAt(4)),170 + 40 * myAtoi(line.charAt(5)));
			int speedX = -myAtoi(line.charAt(6));
			if (myAtoi(line.charAt(8)) == 0) speedX = -speedX;
			int speedY = -myAtoi(line.charAt(7));
			if (myAtoi(line.charAt(9)) == 0) speedY = -speedY;
			enemy.setSpeed(speedX, speedY);
			enemies[i] = enemy;
			enemies[i].setTranslateX(120 + 40 * myAtoi(line.charAt(0)) + 1);
			enemies[i].setTranslateY(170 + 40 * myAtoi(line.charAt(1)) + 1);
			countOfEnemies++;
		}
		
		player = new Character();
		player.setTranslateX(120);
		player.setTranslateY(170); 
		bomb = new Bomb[3];
		for (int i = 0; i < 3; i++) {
		Bomb bomb1 = new Bomb(i + 1);
		bomb[i] = bomb1;
		bomb[i].setVisible(false);
		}
		
		gameRoot.getChildren().add(player);
		gameRoot.getChildren().addAll(bomb);
		gameRoot.getChildren().addAll(enemies);
		
		main_scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
		main_scene.setOnKeyReleased(event -> {
			keys.put(event.getCode(), false);
			if((event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) && player.isAlive() == true)
			player.animationRaL.EndOfAnimation();
			if(event.getCode() == KeyCode.DOWN && player.isAlive() == true)
			player.animationDown.EndOfAnimation();
			if(event.getCode() == KeyCode.UP && player.isAlive() == true)
			player.animationUp.EndOfAnimation();
		});
		
		main_scene.setRoot(gameRoot);
	}

	private void update() {
		if(gameRoot != null) {
			if (player.isAlive() == true) {
				if (isPressed(KeyCode.RIGHT) && player.getTranslateX() + 40 < 885) {
					start = true;
					player.setScaleX(1);
					player.animationRaL.play();
					player.moveX(speedOfBomberman);
				} else if (isPressed(KeyCode.RIGHT)) {
					player.setScaleX(1);
					player.animationRaL.EndOfAnimation();
				}
				if (isPressed(KeyCode.LEFT) && player.getTranslateX() > 121) {
					player.setScaleX(-1);
					player.animationRaL.play();
					player.moveX(-speedOfBomberman);
				} else if (isPressed(KeyCode.LEFT)) {
					player.setScaleX(-1);
					player.animationRaL.EndOfAnimation();
				}
				if (isPressed(KeyCode.UP) && player.getTranslateY() > 170) {
					player.animationUp.play();
					player.moveY(-speedOfBomberman);
				} else if (isPressed(KeyCode.UP)) {
					player.animationUp.EndOfAnimation();
				}
				if (isPressed(KeyCode.DOWN) && player.getTranslateY() < 495) {
					start = true;
					player.animationDown.play();
					player.moveY(speedOfBomberman);
				} else if (isPressed(KeyCode.DOWN)) {
					player.animationDown.EndOfAnimation();
				}
				if (isPressed(KeyCode.SPACE)) {
					start = true;
					for(int i = 0; i < 3; i++) {
						if(bomb[i].isReady()) {
							bomb[i].setBomb();
							break;	
						}	
					}
				}
				if (start == true && player.isAlive()) {
					for (int i = 0; i < countOfEnemies; i++) {
						enemies[i].move();
					}
				}
			}
		}
	}
	
	private boolean isPressed(KeyCode key)	{ return keys.getOrDefault(key, false); }
	
	private Parent createContent() {
		mainRoot.setPrefSize(1000, 600);
		
		Image im = new Image(this.getClass().getResourceAsStream("res/images/MainBomberman.jpg"));
		ImageView img = new ImageView(im);
		img.setFitWidth(1066);
		img.setFitHeight(600);
		
		mainRoot.getChildren().add(img);
		
		menu = new MenuBox(new MenuItem("RESUME GAME", MenuItem.RESUME_GAME),
			   new MenuItem("NEW GAME",MenuItem.NEW_GAME),
			   new MenuItem("QUIT",MenuItem.QUIT));
		
		mainRoot.getChildren().add(menu);
				
		return mainRoot;
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		main_scene = new Scene(createContent());
		main_scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				if (menu.isVisible()) {
					menu.hide();
				}
				else {
					menu.show();
				}
			}
		});
		primaryStage.setTitle("Bomberman");
		primaryStage.setMaxHeight(600);
		primaryStage.setMaxWidth(1000);
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
	
	private static int myAtoi(char c) {
		int preobr = 0;
		if ( c >= '0' && c <= '9') preobr = c - '0';
		if ( c >= 'A' && c <= 'J') preobr = c - 'A' + 10;
		return preobr;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
