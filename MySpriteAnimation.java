package com.alex.bsuir;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class MySpriteAnimation extends Transition{

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
	
	public void SetNumberOfBomb(int temp) {
		numberOfBomb = temp;
	}
	
	public void SetType(int temp) {
		type = temp;
	}
	
	public MySpriteAnimation(
			ImageView imageView,
			Duration duration,
			int count, int columns,
			int offsetX, int offsetY,
			int width, int height
			) {
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
	protected void interpolate(double k) {
		final int index = Math.min((int) Math.floor(k * (count - 1)), count - 2);
		if (index != lastIndex) {
		final int x = (index % columns) * width + offsetX;
		final int y = (index / columns) * height + offsetY;
		imageView.setViewport(new Rectangle2D(x, y, width, height));
		lastIndex = index;
		}
		if (type == 1 && k == 1)
			Main.bomb[numberOfBomb - 1].detonation();
		if (type == 2 && k ==1)
			Main.bomb[numberOfBomb - 1].endOfDetonation();
	}
	
	public void EndOfAnimation() {
		
		imageView.setViewport(new Rectangle2D(0, offsetY, width, height));
		this.stop();
	}
	
}
