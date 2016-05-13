package com.alex.bsuir;

public class GameInfo {
  private String name = null;
  int stepsCountRight = 0, stepsCountLeft = 0, stepsCountUp = 0, stepsCountDown = 0;
  private int enemyStepsCount = 0;
  private int settedBombs = 0;
  private int numberOfLevel = 0;

  public GameInfo() {
    this.name = null;
    this.stepsCountDown = 0;
    this.stepsCountLeft = 0;
    this.stepsCountRight = 0;
    this.stepsCountUp = 0;
    this.enemyStepsCount = 0;
    this.settedBombs = 0;
    this.numberOfLevel = 0;
  }

  public static void analizeInfo() {
    long[] moves = new long[4];
    long[] levels = new long[Constants.CountOfLevels];
    long minMoves, max;
    for (GameInfo temp : Constants.games) {
      moves[0] += temp.stepsCountRight;
      moves[1] += temp.stepsCountLeft;
      moves[2] += temp.stepsCountUp;
      moves[3] += temp.stepsCountDown;
      minMoves = Math.min(Math.min(moves[0], moves[1]), Math.min(moves[2], moves[3]));
      for (int i = 0; i < 4; i++) {
        moves[i] -= minMoves;
      }
      levels[temp.numberOfLevel - 1]++;
    }
    max = Math.max(Math.max(moves[0], moves[1]), Math.max(moves[2], moves[3]));
    System.out.print("Max moves: ");
    if (moves[0] == max) {
      System.out.print("Right ");
    }
    if (moves[1] == max) {
      System.out.print("Left ");
    }
    if (moves[2] == max) {
      System.out.print("Up ");
    }
    if (moves[3] == max) {
      System.out.print("Down");
    }
    max = 0;
    for (long i : levels) {
      if (max < i) {
        max = i;
      }
    }
    System.out.println();
    System.out.print("Max usable levels: ");
    for (int i = 0; i < Constants.CountOfLevels; i++) {
      if (max == levels[i]) {
        System.out.println(i);
      }
    }
  }

  public GameInfo(String name, int stepsR, int stepsL, int stepsU, int stepsD, int enemySteps,
      int settedBombs, int numOfLvl) {
    this.name = name;
    this.stepsCountRight = stepsR;
    this.stepsCountLeft = stepsL;
    this.stepsCountUp = stepsU;
    this.stepsCountDown = stepsD;
    this.enemyStepsCount = enemySteps;
    this.settedBombs = settedBombs;
    this.numberOfLevel = numOfLvl;
  }

  public String getName() {
    return name;
  }

  public int getSteps() {
    return stepsCountDown + stepsCountLeft + stepsCountRight + stepsCountUp;
  }

  public int getEnemySteps() {
    return enemyStepsCount;
  }

  public int getBombs() {
    return settedBombs;
  }

  public int getLevelNumber() {
    return numberOfLevel;
  }

  public String printInfo() {
    String info = new String(name + " Number of level: " + numberOfLevel + " Count of hero steps: "
        + getSteps() + " Count of enemies steps: " + enemyStepsCount + " Count of setted steps: "
        + settedBombs);
    return info;
  }
}
