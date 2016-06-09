package com.alex.bsuir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SaveGame {
  BufferedWriter out = null;
  BufferedReader in = null;
  OutputStream clean;
  FileChooser tr = new FileChooser();
  File dialog = new File("SaveGames");
  File[] allFiles;
  static int countOfSaves = 0;

  SaveGame() {
    allFiles = new File("SaveGames").listFiles();
    countOfSaves = allFiles.length;
    tr.setInitialDirectory(dialog);
  }

  public void openBufferForWrite() {
    if (out != null) {
      return;
    }
    countOfSaves++;
    cleanSave(countOfSaves);
    String path = new String("SaveGames\\moveplayer_number_" + countOfSaves + ".c");

    try {
      out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void openBufferForRead() {
    if (in != null) {
      return;
    }
    try {
      in = new BufferedReader(new InputStreamReader(new FileInputStream(tr.showOpenDialog(null))));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int saveMove(int direction) {
    try {
      if (out == null) {
        return -2;
      } else {
        out.write(direction);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return direction;
  }

  public void newLine() {
    try {
      if (out != null) {
        out.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int getIntFromFile() {
    try {
      if (in == null) {
        return -2;
      }
      int temp = in.read();
      if (temp == -1) {
        closeInputStream();
        Main.endOfSave();
      }
      return temp;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return -1;
  }

  public void closeOutputStream() {
    try {
      if (out != null) {
        saveMove(-1);
        out.close();
        out = null;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void closeInputStream() {
    try {
      if (in != null) {
        in.close();
        in = null;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addLevelNumber(int temp) {
    try {
      out.write(temp);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void cleanSave(int number) {
    String path = new String("SaveGames\\moveplayer_number_" + number + ".c");
    try {
      clean = new FileOutputStream(path);
      clean.flush();
      clean.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showSaves() {
    Stage saves;
    saves = new Stage();
    saves.setTitle("Saves");
    Pane saveRoot = new Pane();
    saveRoot.setPrefSize(900, 500);
    saveRoot.setMaxSize(900, 500);

    ObservableList<String> games = FXCollections.observableArrayList();
    if (Constants.games == null) {
      Constants.save.convertSavesInToInfo();
    }
    for (GameInfo temp : Constants.games) {
      games.add(temp.printInfo());
    }
    ListView<String> listSaves = new ListView<String>();
    listSaves.setItems(games);
    listSaves.setOnMousePressed(e -> {
      transform(listSaves.getSelectionModel().getSelectedIndex());
    });
    listSaves.setStyle(
        "-fx-border-width:3pt;-fx-border-color:red;-fx-font:bold 10pt ItalicT;-fx-text-fill: red;");
    listSaves.setPrefSize(900, 500);
    listSaves.setMaxSize(900, 500);
    saveRoot.getChildren().add(listSaves);
    Scene saveScene = new Scene(saveRoot);
    saves.setScene(saveScene);
    saves.show();
  }

  private void transform(int temp) {
    ObservableList<String> games = FXCollections.observableArrayList();
    if (Constants.games == null) {
      Constants.save.convertSavesInToInfo();
    }
    ListView<String> listSaves = new ListView<String>();
    int tmp = 0;
    int[] mass = new int[5];
    NotationTransformer transformer = new NotationTransformer();
    try {
      in = new BufferedReader(new InputStreamReader(
          new FileInputStream("SaveGames\\" + Constants.games[temp].getName())));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    while (tmp != -1) {
      tmp = readInt();
      switch (tmp) {
        case -1:
          games.add((String) transformer.parse(-1));
          break;
        case 3:
          mass[0] = 3;
          for (int i = 1; i < 5; i++) {
            mass[i] = readInt();
          }
          games.add((String) transformer.parse(mass));
          break;
        case 4:
          mass[0] = 4;
          mass[1] = readInt();
          mass[2] = readInt() + readInt() * 10 + readInt() * 100;
          mass[3] = readInt() + readInt() * 10 + readInt() * 100;
          games.add((String) transformer.parse(mass));
          break;
        case 5:
          mass[0] = 4;
          mass[1] = readInt();
          mass[2] = readInt() + readInt() * 10 + readInt() * 100;
          mass[3] = readInt() + readInt() * 10 + readInt() * 100;
          games.add((String) transformer.parse(mass));
          break;
      }
    }
    listSaves.setItems(games);
    Stage saves;
    saves = new Stage();
    saves.setTitle("Save " + Constants.games[temp].getName());
    Pane saveRoot = new Pane();
    saveRoot.setPrefSize(400, 500);
    saveRoot.setMaxSize(400, 500);
    listSaves.setPrefSize(400, 500);
    listSaves.setMaxSize(400, 500);
    saveRoot.getChildren().add(listSaves);
    Scene saveScene = new Scene(saveRoot);
    saves.setScene(saveScene);
    saves.show();
  }

  private int readInt() {
    int tmp = -2;
    try {
      tmp = in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return tmp;
  }

  public void convertSavesInToInfo() {
    allFiles = new File("SaveGames").listFiles();
    Constants.games = new GameInfo[allFiles.length];
    int count = 0;
    int tmp = -2;
    int numberOfLevel;
    int stepsCountR, stepsCountL, stepsCountU, stepsCountD;
    int enemyStepsCount;
    int settedBombs;
    for (File temp : allFiles) {
      stepsCountR = 0;
      stepsCountL = 0;
      stepsCountU = 0;
      stepsCountD = 0;
      enemyStepsCount = 0;
      settedBombs = 0;
      try {
        in = new BufferedReader(new InputStreamReader(new FileInputStream(temp)));
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      numberOfLevel = readInt();
      do {
        tmp = readInt();
        switch (tmp) {
          case 3:
            if (readInt() == 1) {
              stepsCountR++;
            }
            if (readInt() == 1) {
              stepsCountL++;
            }
            if (readInt() == 1) {
              stepsCountU++;
            }
            if (readInt() == 1) {
              stepsCountD++;
            }

            break;
          case 4:
            readInt();
            if ((readInt()) == -2 || (readInt()) == -2 || (readInt()) == -2 || (readInt()) == -2
                || (readInt()) == -2 || (readInt()) == -2) {
              return;
            } else {
              enemyStepsCount++;
            }
            break;
          case 5:
            readInt();
            if ((readInt()) == -2 || (readInt()) == -2 || (readInt()) == -2 || (readInt()) == -2
                || (readInt()) == -2 || (readInt()) == -2) {
              return;
            } else {
              settedBombs++;
            }
            break;
        }
      } while (tmp != -1);
      closeInputStream();
      Constants.games[count] = new GameInfo(temp.getName(), stepsCountR, stepsCountL, stepsCountU,
          stepsCountD, enemyStepsCount, settedBombs, numberOfLevel + 1);
      count++;
    }
  }

  public static void printAllInfo() {
    for (GameInfo temp : Constants.games) {
      System.out.print(temp.printInfo());
      System.out.println();
    }
  }

  public void sortInfoList() {
    convertSavesInToInfo();
    ScalaSort scalaSort = new ScalaSort();
    long time = System.currentTimeMillis();
    scalaSort.sort(Constants.games);
    time = System.currentTimeMillis() - time;
    System.out.println("Time for scala sort: " + time);
  }

  public void javaSortInfoList() {
    convertSavesInToInfo();
    JavaSort javaSort = new JavaSort();
    long time = System.currentTimeMillis();
    javaSort.qSort(Constants.games, 0, Constants.games.length - 1);
    time = System.currentTimeMillis() - time;
    System.out.println("Time for java sort: " + time);
  }
}
