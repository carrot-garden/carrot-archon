package com.carrotgarden.trader.dukascopy
import com.weiglewilczek.slf4s.Logging
import java.io.File
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.io.FileInputStream
import SevenZip.Compression.LZMA.Decoder
import java.io.FileOutputStream
import java.io.DataInputStream
import java.io.ByteArrayOutputStream
import com.dukascopy.charts.data.datacache.TickData
import com.dukascopy.api.Instrument
import com.carrotgarden.util.values.provider.ValueBuilder
import com.carrotgarden.util.values.lang.MathExtra

object MainParse extends Logging {

  def main(args: Array[String]): Unit = {

    logger.info("init");

    val file = new File(Const.FILE_INST_START)

    val text = Util.fileLoad(file);

    val map = Json.mapFrom(text);

    val done = new DateTime("2012-03-29T00:00:00.000Z")

    val futureList = for {
      (name, init) <- map
    } yield {
      syncPeriod(
        name,
        init.withZone(DateTimeZone.UTC),
        done.withZone(DateTimeZone.UTC))
    }

    logger.info("done");

  }

  def syncPeriod(name: String, init: DateTime, done: DateTime) = {

    logger.info("period; name=" + name);

    val futureArray = for {
      day <- Util.rangeDays(init, done)
    } yield {
      syncDay(name, day)
    }

  }

  def syncDay(name: String, day: DateTime) = {

    logger.info(" name=" + name + " day=" + day);

    val futureArray = for {
      hour <- Util.rangeHours(day)
    } yield {
      syncHour(name, hour)
    }

  }

  val base = new File(Const.FOLDER)

  val ZIP_PROP_SIZE = 5
  val ZIP_DATA_SIZE = 8

  val MASK = 0xFFL

  def decodeLong(array: Array[Byte]): Long = {

    //    for (index <- 0 until 8) {
    //      logger.info("array=" + index + ":" + (array(index) & MASK));
    //    }

    var result = 0L

    result |= ((array(0) & MASK) << 0)
    result |= ((array(1) & MASK) << 8)
    result |= ((array(2) & MASK) << 16)
    result |= ((array(3) & MASK) << 24)
    result |= ((array(4) & MASK) << 32)
    result |= ((array(5) & MASK) << 40)
    result |= ((array(6) & MASK) << 48)
    result |= ((array(7) & MASK) << 56)

    result

  }

  val TICK_VERS = 5;
  val TICK_SIZE = TickData.getLength(TICK_VERS);

  def syncHour(name: String, hour: DateTime): Unit = {

    val source = Util.tickLocalURL(base, name, hour)

    logger.info("source=" + source);

    val (isSourcePresent, localTime, localSize) = Util.isPresent(source)

    //

    val file = new File(source.toURI)

    val fileSize = file.length

    val input = new FileInputStream(file)

    val dataInput = new DataInputStream(input)

    //

    if (fileSize < ZIP_PROP_SIZE + ZIP_DATA_SIZE) {
      logger.error("file is too short")
      return
    }

    val propArray = new Array[Byte](ZIP_PROP_SIZE)

    dataInput.readFully(propArray)

    //    var index = 0; var count = 0;
    //    do {
    //      count = input.read(propsArray, index, propsSize - index)
    //      logger.info("count=" + count)
    //      if (count > 0) {
    //        index += count;
    //      }
    //    } while (count > 0)
    //    if (index != propsSize) {
    //      logger.error("file is too short")
    //      return
    //    }

    val decoder = new Decoder()

    if (!decoder.SetDecoderProperties(propArray)) {
      logger.error("invalid seven zip props")
      return
    }

    val sizeArray = new Array[Byte](ZIP_DATA_SIZE)

    dataInput.readFully(sizeArray)

    val dataSize = decodeLong(sizeArray)
    //    logger.debug("dataSize=" + dataSize)

    val output = new ByteArrayOutputStream(dataSize.toInt)
    //    val output = new FileOutputStream("./target/test.bin")

    decoder.Code(input, output, dataSize)

    val dataOutput = output.toByteArray

    //

    val tick = new TickData();

    val hourStart = hour.getMillis

    val inst = Instrument.valueOf(name);

    val pipValue = inst.getPipValue

    var index = 0;

    while (index < dataSize) {

      tick.fromBytes(TICK_VERS, hourStart, pipValue, dataOutput, index);

      val time = tick.time

      val bidPrice = MathExtra.extractDecimal(tick.bid)
      val bidSize = MathExtra.extractDecimal(tick.bidVol)

      val askPrice = MathExtra.extractDecimal(tick.ask)
      val askSize = MathExtra.extractDecimal(tick.askVol)

      //

      logger.debug("bidPrice = " + bidPrice.getMantissa + "/" + bidPrice.getExponent)
      logger.debug("bidSize = " + bidSize.getMantissa + "/" + bidSize.getExponent)

      logger.debug("askPrice = " + askPrice.getMantissa + "/" + askPrice.getExponent)
      logger.debug("askSize = " + askSize.getMantissa + "/" + askSize.getExponent)

      val date = new DateTime(time).withZone(DateTimeZone.UTC)

      //      logger.debug("%s %f@%f  / %f@%f".format(date, bidPrice, bidSize, askPrice, askSize))

      index += TICK_SIZE

      //      logger.debug("TICK_SIZE=" + TICK_SIZE)

    }

  }

}
