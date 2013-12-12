package com.carrotgarden.trader.dukascopy

import java.io.File
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.net.URL
import com.weiglewilczek.slf4s.Logging
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.FileNotFoundException
import java.net.ConnectException
import java.io.InputStream
import java.net.HttpURLConnection
import org.apache.http.HttpResponse
import org.apache.http.HttpHeaders
import org.apache.http.impl.cookie.DateUtils
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpHead
import org.apache.http.HttpStatus
import org.apache.http.util.EntityUtils

object Util extends Logging {

  def fileSave(file: File, text: String) {

    import java.io.PrintWriter

    val writer = new PrintWriter(file)

    try {

      writer.println(text)

    } finally {

      writer.close()

    }

  }

  def fileLoad(file: File): String = {

    import scala.io.Source._

    val source = fromFile(file)

    try {

      source.mkString

    } finally {

      source.close()

    }

  }

  def tickRemoteURL(name: String, time: DateTime): URL = {

    val timeUTC = time.withZone(DateTimeZone.UTC)

    val dukasName = name

    val dukasYear = timeUTC.getYear
    val dukasMonth = timeUTC.getMonthOfYear - 1 // XXX
    val dukasDay = timeUTC.getDayOfMonth
    val dukasHour = timeUTC.getHourOfDay

    val template = Const.DUKAS_TICK_URL

    val path = template.format(dukasName, dukasYear, dukasMonth, dukasDay, dukasHour)

    new URL(path)

  }

  def tickLocalURL(base: File, name: String, time: DateTime): URL = {

    val timeUTC = time.withZone(DateTimeZone.UTC)

    val fileName = name.toLowerCase

    val fileYear = timeUTC.getYear
    val fileMonth = timeUTC.getMonthOfYear
    val fileDay = timeUTC.getDayOfMonth
    val fileHour = timeUTC.getHourOfDay

    val template = Const.FILE_TICK_PATH

    val path = template.format(fileName, fileYear, fileMonth, fileDay, fileHour)

    val file = new File(base, path)

    file.getParentFile().mkdirs()

    file.toURL

  }

  def rangeDays(init: DateTime, done: DateTime): Array[DateTime] = {

    import scala.collection.mutable.ArrayBuffer

    val buffer = new ArrayBuffer[DateTime]

    var time = init;

    while (time.isBefore(done)) {
      buffer += time
      time = time.plusDays(1)
    }

    buffer += done

    buffer.toArray

  }

  def rangeHours(time: DateTime): Array[DateTime] = {

    import scala.collection.mutable.ArrayBuffer

    val buffer = new ArrayBuffer[DateTime]

    for {
      hour <- 0 until 24
    } {
      buffer += time.withHourOfDay(hour)
    }

    buffer.toArray

  }

  val USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0a2) Gecko/20110613 Firefox/6.0a2"

  private def isPresentFile(url: URL): (Boolean, Long, Long) = {

    val file = new File(url.toURI)

    val time = file.lastModified
    val size = file.length

    (file.exists, time, size)

  }

  private def isPresentHttp(url: URL): (Boolean, Long, Long) = {

    val con = url.openConnection.asInstanceOf[HttpURLConnection]

    //    var input: InputStream = null

    try {

      con.addRequestProperty("User-Agent", USER_AGENT);

      con.setRequestMethod("HEAD");

      //      input = con.getInputStream

      val code = con.getResponseCode

      val time = con.getLastModified
      val size = con.getContentLength

      code match {
        case HttpURLConnection.HTTP_OK =>
          (true && time > 0 && size > 0, time, size)
        case _ =>
          (false, time, size)
      }

    } catch {

      case e: Exception =>
        logger.error("url=" + url)
        logger.error("err", e)
        (false, 0, 0)

    } finally {

      //      if (input != null) {
      //        input.close
      //      }

    }

  }

  // (isOK, time , size)
  def isPresent(url: URL): (Boolean, Long, Long) = {

    if ("file" == url.getProtocol) {
      return isPresentFile(url)
    }

    if ("http" == url.getProtocol) {
      return isPresentHttp(url)
    }

    throw new UnsupportedOperationException("unknown protocol : " + url.getProtocol)

  }

  def fileSave(source: URL, target: URL, copySize: Int = 64 * 1024) {

    val sourceConn = source.openConnection

    sourceConn.addRequestProperty("User-Agent", USER_AGENT);

    val sourceStream = new BufferedInputStream(sourceConn.getInputStream);

    val sourceTime = sourceConn.getLastModified
    val sourceSize = sourceConn.getContentLength

    //

    val targetFile = new File(target.toURI)
    val targetStream = new FileOutputStream(targetFile)

    //

    val parent = targetFile.getParentFile
    if (!parent.exists) {
      targetFile.getParentFile.mkdirs
    }

    //

    val array = new Array[Byte](copySize)

    var isPending = false

    do {

      val size = sourceStream.read(array, 0, copySize);

      isPending = (size > -1)

      if (isPending) {
        targetStream.write(array, 0, size)
      }

    } while (isPending)

    targetStream.flush

    sourceStream.close
    targetStream.close

    targetFile.setLastModified(sourceTime)

    val targetTime = targetFile.lastModified
    val targetSize = targetFile.length

    assert(sourceTime == targetTime && sourceSize == targetSize)

  }

  def ensureFolder(file: File): Unit = {
    val folder = file.getParentFile
    if (!folder.exists) {
      folder.mkdirs
    }
  }

  def fetchHeader(response: HttpResponse, name: String): Option[String] = {

    val entry = response.getFirstHeader(name)

    if (entry == null) {
      logger.error("missing http header : " + name)
      None
    } else {
      Option(entry.getValue)
    }

  }

  def fileSaveOK(response: HttpResponse, file: File): Unit = {

    val someDate = fetchHeader(response, HttpHeaders.LAST_MODIFIED)
    val someSize = fetchHeader(response, HttpHeaders.CONTENT_LENGTH)

    (someDate, someSize) match {

      case (Some(textDate), Some(textSize)) =>

        val httpTime = DateUtils.parseDate(textDate).getTime
        val httpSize = Integer.parseInt(textSize)

        //        logger.info("textDate=" + textDate)
        //        logger.info("textSize=" + textSize)

        val entity = response.getEntity

        if (entity == null) {
          logger.error("entity == null")
          return
        }

        ensureFolder(file)

        val output = new FileOutputStream(file)
        entity.writeTo(output)
        output.flush
        output.close

        file.setLastModified(httpTime)

        val fileSize = file.length
        val fileTime = file.lastModified

        assert(fileSize == httpSize && fileTime == httpTime)

      case _ =>
        logger.error("missing headers")

    }

  }

  def fileSave(response: HttpResponse, file: File): Unit = {

    val code = response.getStatusLine.getStatusCode

    code match {

      case HttpStatus.SC_OK =>
        //        logger.error("response=" + response)
        fileSaveOK(response, file)

      case HttpStatus.SC_NOT_FOUND =>
        logger.error("code=" + code)

      case _ =>
        logger.error("code=" + code)

    }

    EntityUtils.consume(response.getEntity)

  }

  // XXX
  def isPresent(client: HttpClient, url: URL): (Boolean, Long, Long) = {

    if ("file" == url.getProtocol) {
      return isPresentFile(url)
    }

    if ("http" == url.getProtocol) {

      val header = new HttpHead(url.toURI)

      val response = client.execute(header)

      val code = response.getStatusLine().getStatusCode

      code match {

        case HttpStatus.SC_OK =>
          (false, 0, 0)

        case HttpStatus.SC_NOT_FOUND =>
          (false, 0, 0)

        case _ =>
          logger.error("code=" + code)
          (false, 0, 0)

      }

      EntityUtils.consume(response.getEntity)

    }

    throw new UnsupportedOperationException("unknown protocol : " + url.getProtocol)

  }

}
