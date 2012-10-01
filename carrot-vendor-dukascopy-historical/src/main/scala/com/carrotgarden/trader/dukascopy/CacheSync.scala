package com.carrotgarden.trader.dukascopy

import com.weiglewilczek.slf4s.Logging
import java.io.File
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import scala.actors.Future
import scala.actors.Futures._
import java.net.URL

object CacheFetch extends Logging {

  val base = new File("/work/download/dukascopy/cache")

  def isSyncDone(local: URL, remote: URL): Boolean = {

    val (isLocalPresent, localTime, localSize) = Util.isPresent(local)

    val (isRemotePresent, remoteTime, remoteSize) = Util.isPresent(remote)

    if (isRemotePresent) {
      isLocalPresent && localTime == remoteTime && localSize == remoteSize
    } else {
      true
    }

  }

  private var countHour = 0L

  def syncHour(name: String, hour: DateTime): Unit = {

    val local = Util.tickLocalURL(base, name, hour)
    val remote = Util.tickRemoteURL(name, hour)

    //    logger.info("remote=" + remote);
    //    logger.info("local=" + local);

    countHour += 1

    if (countHour % 100 == 0) {
      logger.info("countHour=" + countHour);
    }

    val (isLocalPresent, localTime, localSize) = Util.isPresent(local)

    if (isLocalPresent) {
      return
    }

    val (isRemotePresent, remoteTime, remoteSize) = Util.isPresent(remote)

    if (!isRemotePresent) {
      return
    }

    Util.fileSave(remote, local)

    logger.info("NEW " + local);

  }

  def syncDay(name: String, day: DateTime) = {

    logger.info(" name=" + name + " day=" + day);

    val futureArray = for {
      hour <- Util.rangeHours(day)
    } yield {
      //      future {
      syncHour(name, hour)
      //      }
    }

    //    awaitAll(10 * 60 * 1000, futureArray: _*);

  }

  def syncPeriod(name: String, init: DateTime, done: DateTime) = {

    logger.info("period; name=" + name);

    val futureArray = for {
      day <- Util.rangeDays(init, done)
    } yield {
      //      future {
      syncDay(name, day)
      //      }
    }

    //    awaitAll(10 * 60 * 1000, futureArray: _*);

  }

  def main(args: Array[String]): Unit = {

    logger.info("init");

    System.setProperty("actors.corePoolSize", "30")

    val file = new File(Const.FILE_INST_START)

    val text = Util.fileLoad(file);

    val map = Json.mapFrom(text);

    //    logger.info("map=" + map);

    val done = new DateTime("2012-03-24T00:00:00.000Z")

    val futureList = for {
      (name, init) <- map
    } yield {
      future {
        syncPeriod(
          name,
          init.withZone(DateTimeZone.UTC),
          done.withZone(DateTimeZone.UTC))
      }
    }

    awaitAll(10 * 60 * 60 * 1000, futureList.toArray: _*);

    logger.info("done");

  }

}
