package com.carrotgarden.trader.dukascopy

import org.joda.time.DateTime
import net.liftweb.json.ext.JodaTimeSerializers

object Json {

  import net.liftweb.json._
  import net.liftweb.json.Extraction._

  import net.liftweb.json.Serialization
  import net.liftweb.json.DefaultFormats
  import net.liftweb.json.NoTypeHints

  //    implicit val formats = net.liftweb.json.DefaultFormats
  //    implicit val formats = Serialization.formats(NoTypeHints) + new InstrumentJsonCodec
  //    implicit val formats = net.liftweb.json.DefaultFormats + new InstrumentJsonCodec
  implicit val formats = net.liftweb.json.DefaultFormats ++ JodaTimeSerializers.all

  def jsonFrom(map: Map[String, DateTime]): String = {

    pretty(render(decompose(map)))

  }

  def mapFrom(text: String): Map[String, DateTime] = {

    parse(text).extract[Map[String, DateTime]]

  }

}
