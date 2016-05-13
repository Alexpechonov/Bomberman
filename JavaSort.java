package com.alex.bsuir;

import java.util.Random;

public class JavaSort {
  static Random rand = new Random();
  public void qSort(GameInfo[] array, int begin, int end) {
    int i = begin;
    int j = end;
    int temp = array[begin + rand.nextInt(end - begin + 1)].getSteps();
    while (i <= j) {
      while (array[i].getSteps() > temp) {
        i++;
      }
      while (array[j].getSteps() < temp) {
        j--;
      }
      if (i <= j) {
        GameInfo tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
        i++;
        j--;
      }
    }
    if (begin < j) {
      qSort(array, begin, j);
    }
    if (i < end) {
      qSort(array, i, end);
    }
  }
}
