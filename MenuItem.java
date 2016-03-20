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

public class MenuItem extends StackPane{
	
	public static final int RESUME_GAME = 0;
	public static final int NEW_GAME = 1;
	public static final int QUIT = 2;
	
	public MenuItem(String name,int type) {
		
		Rectangle menuRect = new Rectangle(300, 24);
		
		LinearGradient gradient = 
				new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
				new Stop(0, Color.BLACK),
				new Stop(0.2, Color.YELLOW)
		});
		
		LinearGradient gradientPressed = 
				new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
				new Stop(0, Color.BLACK),
				new Stop(0.5, Color.RED)
		});
		
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
			if(type == QUIT) {
				System.exit(0); 
			}
			if(type == NEW_GAME) {
				Main.createPlayContent();
			}
		});
		
	}
}

