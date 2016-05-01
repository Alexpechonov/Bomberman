package com.alex.bsuir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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
      }
      out.write(direction);
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
    saveRoot.setPrefSize(300, 200);
    saveRoot.setMaxSize(300, 200);

    ObservableList<String> games = FXCollections.observableArrayList();
    allFiles = new File("SaveGames").listFiles();
    for (File temp : allFiles) {
      games.add(temp.getName());
    }
    ListView<String> listSaves = new ListView<String>();
    listSaves.setItems(games);
    listSaves.setStyle(
        "-fx-border-width:3pt;-fx-border-color:red;-fx-font:bold 10pt ItalicT;-fx-text-fill: red;");
    listSaves.setPrefSize(300, 200);
    listSaves.setMaxSize(300, 200);
    saveRoot.getChildren().add(listSaves);
    Scene saveScene = new Scene(saveRoot);
    saves.setScene(saveScene);
    saves.show();
  }
}
