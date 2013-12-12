package com.carrotgarden.trader.dukascopy

import com.weiglewilczek.slf4s.Logging
import java.io.File
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import scala.actors.Future
import scala.actors.Futures._
import java.net.URL
import java.util.concurrent.atomic.AtomicLong
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.HttpClient
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
import org.apache.http.impl.client.DefaultHttpClient

object CacheSync extends Logging {

  val base = new File(Const.FOLDER)

  private var countHour = new AtomicLong

  def syncHour(client: HttpClient, name: String, hour: DateTime): Unit = {

    val local = Util.tickLocalURL(base, name, hour)
    val remote = Util.tickRemoteURL(name, hour)

    //    logger.info("remote=" + remote);
    //    logger.info("local=" + local);

    countHour.getAndIncrement

    if (countHour.get % 100 == 0) {
      logger.info("countHour=" + countHour);
    }

    val (isLocalPresent, localTime, localSize) = Util.isPresent(local)

    if (isLocalPresent) {
      return
    }

    val response = client.execute(new HttpGet(remote.toURI))

    val file = new File(local.toURI)

    Util.fileSave(response, file)

    logger.info("REM : " + remote);
    logger.info("LOC : " + local);

  }

  def syncDay(client: HttpClient, name: String, day: DateTime) = {

    logger.info(" name=" + name + " day=" + day);

    val futureArray = for {
      hour <- Util.rangeHours(day)
    } yield {
      //      future {
      syncHour(client, name, hour)
      //      }
    }

    //    awaitAll(10 * 60 * 1000, futureArray: _*);

  }

  def syncPeriod(client: HttpClient, name: String, init: DateTime, done: DateTime) = {

    logger.info("period; name=" + name);

    val futureArray = for {
      day <- Util.rangeDays(init, done)
    } yield {
      //      future {
      syncDay(client, name, day)
      //      }
    }

    //    awaitAll(10 * 60 * 1000, futureArray: _*);

  }

  def main(args: Array[String]): Unit = {

    logger.info("init");

    val manager = new ThreadSafeClientConnManager
    manager.setMaxTotal(50)
    manager.setDefaultMaxPerRoute(10)

    val client = new DefaultHttpClient(manager)

    //

    System.setProperty("actors.corePoolSize", "100")

    val file = new File(Const.FILE_INST_START)

    val text = Util.fileLoad(file);

    val map = Json.mapFrom(text);

    //    logger.info("map=" + map);

    // keep one day old
    val done = new DateTime("2012-03-29T00:00:00.000Z")

    val futureList = for {
      (name, init) <- map
    } yield {
      future {
        syncPeriod(
          client,
          name,
          init.withZone(DateTimeZone.UTC),
          done.withZone(DateTimeZone.UTC))
      }
    }

    awaitAll(10 * 60 * 60 * 1000, futureList.toArray: _*);

    client.getConnectionManager.shutdown

    logger.info("done");

  }

}
