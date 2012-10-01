package com.carrotgarden.trader.dukascopy

object Const {

  val FOLDER = "/work/download/historical/dukascopy/ticks"

  val FILE_INST_START = "./src/main/resources/instrument-start-time.json"

  // case sensitive
  val FILE_TICK_PATH = "/%s/%04d/%02d/%02d/%02dh_ticks.bi5"

  // case sensitive
  val DUKAS_TICK_URL = "http://www.dukascopy.com/datafeed" + FILE_TICK_PATH

}
