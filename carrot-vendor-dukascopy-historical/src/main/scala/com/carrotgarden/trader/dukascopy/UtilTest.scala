package com.carrotgarden.trader.dukascopy
import com.weiglewilczek.slf4s.Logging
import java.net.URL

object UtilTest extends Logging {

  def main(args: Array[String]): Unit = {

    logger.info("init");

    var url = "http://www.dukascopy.com/datafeed/GBPCAD/2010/02/06/00h_ticks.bi5"

    for {
      index <- 0 until 10
    } {

      Util.isPresent(new URL(url))

      logger.info("index=" + index);

    }

    logger.info("done");

  }

}
