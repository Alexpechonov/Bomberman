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

public class MenuBox extends StackPane{
		public MenuBox(MenuItem...items) {
			Rectangle main = new Rectangle(300,600);
			main.setOpacity(0.2);
			
			DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
			shadow.setSpread(0.8);
			
			main.setEffect(shadow);
			
			Line vSep = new Line();
			vSep.setStartX(300);
			vSep.setEndX(300);
			vSep.setEndY(600);
			vSep.setStroke(Color.DARKRED);
			vSep.setOpacity(0.4);
			
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.TOP_RIGHT);
			vbox.setPadding(new Insets(85, 0, 0, 0));
			vbox.getChildren().addAll(items);
			
			setAlignment(Pos.TOP_RIGHT);
			getChildren().addAll(main, vSep, vbox);
		}
		
		public void show() {
			setVisible(true);
			TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), this);
			trans.setToX(0);
			trans.play();
		}
		
		public void hide() {
			TranslateTransition trans = new TranslateTransition(Duration.seconds(0.5), this);
			trans.setToX(-300);
			trans.setOnFinished(event -> setVisible(false));
			trans.play();

		}
	
}
