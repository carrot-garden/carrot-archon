package com.carrotgarden.trader.dukascopy

import net.liftweb.json.Serializer
import com.dukascopy.api.Instrument
import net.liftweb.json.Formats
import net.liftweb.json.TypeInfo
import net.liftweb.json.JsonAST.JObject
import net.liftweb.json.JsonAST.JValue
import net.liftweb.json.MappingException
import net.liftweb.json.JsonAST.JField
import net.liftweb.json.JsonAST.JInt
import net.liftweb.json.JsonAST.JString

class InstrumentJsonCodec extends Serializer[Instrument] {

  private val klaz = classOf[Instrument]

  def serialize(implicit formats: Formats): PartialFunction[Any, JValue] = {
    case inst: Instrument =>
      JString(inst.name)
    case other =>
      throw new MappingException("Can't convert " + other + " from Instrument")
  }

  def deserialize(implicit formats: Formats): PartialFunction[(TypeInfo, JValue), Instrument] = {
    case (TypeInfo(klaz, _), json) => json match {
      case JString(text) =>
        Instrument.valueOf(text)
      case other =>
        throw new MappingException("Can't convert " + other + " into Instrument")
    }
  }

}
