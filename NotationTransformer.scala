package com.alex.bsuir

class NotationTransformer {
  def parse(temp: Any): Any = {
    temp match {
      case mas: Array[Int] => getOutputMessage(mas)
      case -1 => "Game is end."
      case _ => "TYPE ERROR"
    }
  }

  def getOutputMessage(mas: Array[Int]): String = {
    if (mas(0) == 3) {
      if (mas(1) == 1) {
        "Hero moves right"
      } else if (mas(2) == 1) {
        "Hero moves left"
      } else if (mas(3) == 1) {
        "Hero moves up"
      } else if (mas(4) == 1) {
        "Hero moves down"
      } else {
        "Hero stay"
      }
    } else if (mas(0) == 4) {
      "Enemy " + Integer.toString(mas(1)) + " move to new position (" + Integer.toString(mas(2)) + ',' + Integer.toString(mas(3)) + ')'
    } else if (mas(0) == 5) {
      "Bomb " + Integer.toString(mas(1)) + " set on position (" + Integer.toString(mas(2)) + ',' + Integer.toString(mas(3)) + ')'
    } else {
      ""
    }
  }
}