package com.carrotgarden.trader.dukascopy

import com.dukascopy.api.Instrument
import org.joda.time.DateTime
import java.net.URL
import org.joda.time.DateTimeZone
import net.liftweb.json.ext.JodaTimeSerializers
import java.io.File
import java.io.PrintWriter
import org.joda.time.Days
import com.weiglewilczek.slf4s.Logging
import java.io.InputStream

object FindStartTime extends Logging {

  def isPresent(name: String, time: DateTime): Boolean = {

    val url = Util.tickRemoteURL(name, time)

    val con = url.openConnection

    var input: InputStream = null

    try {
      input = con.getInputStream
      true
    } catch {
      case _ => false
    } finally {
      if (input != null) input.close
    }

  }

  def find(name: String, init: DateTime, done: DateTime): Option[DateTime] = {

    val days = Days.daysBetween(init, done).getDays

    if (days < 1) {
      logger.error("not found: days < 1");
      None
    }

    val combo = (isPresent(name, init), isPresent(name, done))

    combo match {
      case (false, true) =>

        var min = 0; var max = days;

        var last = done

        while (min <= max) {

          val avg = Math.round((min + max) / 2.0F)

          val next = init.plusDays(avg)

          if (isPresent(name, next)) {
            last = next
            max = avg - 1
          } else {
            min = avg + 1
          }

        }

        logger.info("found;" + " name=" + name + " last=" + last)
        Option(last)

      case _ =>

        logger.error("not found; name=" + name)
        None

    }

  }

  def main(args: Array[String]): Unit = {

    logger.info("init");

    val init = new DateTime("2000-01-01T00:00:00.000Z").withZone(DateTimeZone.UTC)
    val done = new DateTime("2012-03-24T00:00:00.000Z").withZone(DateTimeZone.UTC)

    //    val range = daysRange(init, done).sortWith(_.getMillis < _.getMillis)

    import scala.collection.JavaConversions._
    import scala.collection.mutable.ConcurrentMap
    import java.util.concurrent.ConcurrentHashMap

    val map: ConcurrentMap[String, DateTime] = new ConcurrentHashMap[String, DateTime]

    //    import scala.actors._
    //    import scala.actors.Actor._
    import scala.actors.Futures._

    val futureArray = for {
      name <- Instrument.values.map(_.name)
    } yield {
      future {
        find(name, init, done) match {
          case Some(time) =>
            map.put(name, time)
          case None =>
        }
      }
    }

    awaitAll(2 * 60 * 1000, futureArray: _*);

    val file = new File(Const.FILE_INST_START)

    val text = Json.jsonFrom(map.toMap)

    Util.fileSave(file, text)

    logger.info("done");

  }

}
