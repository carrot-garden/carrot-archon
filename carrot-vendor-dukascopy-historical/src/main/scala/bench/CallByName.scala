package bench

class Logger(level: Int) {

  def log(logLevel: Int, message: => String) =
    if (level >= logLevel) println(message)

  def debug(message: => String) = log(10, message)
  def error(message: => String) = log(1, message)

  def javaDebug(message: String) = if (level >= 10) println(message)

}

object Logger extends App {

  // this prints out a value when it's called
  // and how we can know when an expression containing
  // it was evaluated
  def breadcrumb(index: Int): String = {
    println("BREADCRUMB : " + index)
    "..."
  }

  var logger = new Logger(1)

  logger.debug("This is a simple debug message" + breadcrumb(0))

  var complex = "complicated"
  val messageType = "test debug message"

  logger.debug("This is a "
    + complex + " debug "
    + messageType + breadcrumb(1))

  complex = "mucho complicated"

  logger.javaDebug("This is a "
    + complex + " debug "
    + messageType + breadcrumb(2))

  logger.error("RED ALERT" + breadcrumb(3))

}
