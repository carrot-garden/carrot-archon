package com.carrotgarden.trader.dukascopy

class Main {

}

object Main {

  def main(args: Array[String]): Unit = {

    var capitalMap = Map("US" -> "Washington", "France" -> "Paris")

    capitalMap += ("Japan" -> "Tokyo")
    capitalMap += ("Russia" -> "Moscow")

    println(capitalMap("France"))
    println(capitalMap("Russia"))

    var url = "http://freeserv.dukascopy.com/exp/exp.php?fromD=01.01.2004&np=2000&interval=60&DF=Y%2Fm%2Fd&Stock=333&endSym=unix&split=tab"

  }

}
