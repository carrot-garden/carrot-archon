package bench

import org.apache.http.client.ResponseHandler
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpHead
import com.weiglewilczek.slf4s.Logging
import org.apache.http.HttpHeaders
import org.joda.time.DateTime
import org.apache.http.impl.cookie.DateUtils
import java.util.concurrent.atomic.AtomicLong
import scala.actors.Future
import scala.actors.Futures._
import org.apache.http.util.EntityUtils
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
import org.apache.http.HttpResponse
import org.apache.http.HttpStatus
import java.io.FileOutputStream
import java.io.File
import com.carrotgarden.trader.dukascopy.Util

object ApacheClient extends Logging {

  def main(args: Array[String]): Unit = {

    logger.info("init")

    val manager = new ThreadSafeClientConnManager
    manager.setMaxTotal(50)
    manager.setDefaultMaxPerRoute(10)

    val client = new DefaultHttpClient(manager)

    val counter = new AtomicLong

    val header = new HttpHead("http://www.dukascopy.com/datafeed/EURUSD/2012/01/01/01h_ticks.bi5")
    //        val header = new HttpHead("http://www.dukascopy.com/datafeed/GBPCAD/2010/02/06/23h_ticks.bi5")

    val futureArray = for {

      index <- 1 to 100

    } yield {

      future {

        val getter = new HttpGet("http://www.dukascopy.com/datafeed/EURUSD/2012/01/01/01h_ticks.bi5")

        val response = client.execute(getter)

        val code = response.getStatusLine().getStatusCode

        code match {

          case HttpStatus.SC_OK =>
          //            Util.fileSave(response, "" + index)

          case HttpStatus.SC_NOT_FOUND =>
            logger.error("code=" + code)

          case _ =>
            logger.error("code=" + code)

        }

        EntityUtils.consume(response.getEntity)

        counter.getAndIncrement

      }

    }

    val timeStart = System.currentTimeMillis

    awaitAll(3 * 60 * 1000, futureArray: _*)

    val timeFinish = System.currentTimeMillis

    val timeDiff = timeFinish - timeStart

    logger.info("counter=" + counter + " millis/step=" + timeDiff / counter.get)

    client.getConnectionManager.shutdown

    logger.info("done")

  }

}