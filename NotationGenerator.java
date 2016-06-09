package com.alex.bsuir;

import java.util.Random;

public class NotationGenerator {
  int numberOfLevel;
  int timeForGame;
  long time;
  static Random random = new Random();

  public void generateSaves(/* int count */) {
    for (int i = 0; i < 2; i++) {
      Main.countOfMoves = 0;
      Main.countOfBombs = 0;
      numberOfLevel = random.nextInt(3);
      Main.createPlayContent(numberOfLevel);
      Constants.save.openBufferForWrite();
      Constants.save.addLevelNumber(numberOfLevel);
      Main.activeAI = true;
      timeForGame = 10 + random.nextInt(20);
      long timeStart = System.currentTimeMillis();
      do {
        time = System.currentTimeMillis();
        Main.update();
      } while ((time - timeStart) < timeForGame);
      Main.endGame();
    }
  }
}
