package com.alex.bsuir;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private static Scene main_scene;
	
	private MenuBox menu;
	public static ArrayList<GameItem> platforms = new ArrayList<>();
	private static HashMap<KeyCode,Boolean> keys = new HashMap<>();
	
	public static Pane mainRoot = new Pane();
	public static Pane gameRoot = new Pane();
	
	public static Character player;
	public static Bomb bomb;
	
	private int speedOfBomberman = 2;
	
	public static void createPlayContent() {
		
		gameRoot.setPrefSize(600, 1000);
		
		Image background = new Image(Main.class.getResourceAsStream("res/images/grass.jpg"));
		ImageView img = new ImageView(background);
		img.setFitWidth(1000);
		img.setFitHeight(600);
		
		Image playSpace = new Image(Main.class.getResourceAsStream("res/images/sea.jpg"));
		ImageView image = new ImageView(playSpace);
		image.setX(800);
		image.setY(425);
		image.setTranslateX(100);
		image.setTranslateY(150);
		
		gameRoot.getChildren().addAll(img, image);
		
		for(int i = 0; i < LevelData.levels[0].length; i++) {
			String line = LevelData.levels[0][i];
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
		
		player = new Character();
		player.setTranslateX(120);
		player.setTranslateY(170); 
		bomb = new Bomb();
		bomb.setVisible(false);
		gameRoot.getChildren().addAll(player, bomb);
		
		main_scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
		main_scene.setOnKeyReleased(event -> {
			keys.put(event.getCode(), false);
			if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
			player.animationRaL.EndOfAnimation();
			if(event.getCode() == KeyCode.DOWN)
			player.animationDown.EndOfAnimation();
			if(event.getCode() == KeyCode.UP)
			player.animationUp.EndOfAnimation();
		});
		
		main_scene.setRoot(gameRoot);
	}

	private void update() {
		if (isPressed(KeyCode.RIGHT) && player.getTranslateX() + 40 < 885) {
			player.setScaleX(1);
			player.animationRaL.play();
			player.moveX(speedOfBomberman);
		}
		if (isPressed(KeyCode.LEFT) && player.getTranslateX() > 121) {
			player.setScaleX(-1);
			player.animationRaL.play();
			player.moveX(-speedOfBomberman);
		}
		if (isPressed(KeyCode.UP) && player.getTranslateY() > 170) {
			player.animationUp.play();
			player.moveY(-speedOfBomberman);
		}
		if (isPressed(KeyCode.DOWN) && player.getTranslateY() < 495) {
			player.animationDown.play();
			player.moveY(speedOfBomberman);
		}
		if (isPressed(KeyCode.SPACE))
			bomb.setBomb();
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
		main_scene = new Scene(createContent()/*createPlayContent()*/);
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
		/*main_scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
		main_scene.setOnKeyReleased(event -> {
			keys.put(event.getCode(), false);
			if(event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
			player.animationRaL.EndOfAnimation();
			if(event.getCode() == KeyCode.DOWN)
			player.animationDown.EndOfAnimation();
			if(event.getCode() == KeyCode.UP)
			player.animationUp.EndOfAnimation();
		});*/
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
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
