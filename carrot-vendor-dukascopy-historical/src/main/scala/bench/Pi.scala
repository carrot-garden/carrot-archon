package bench

import akka.actor._
import akka.routing.RoundRobinRouter
import akka.util.Duration
import akka.util.duration._
import akka.event.Logging

sealed trait MsgBase

case class MsgStart extends MsgBase
case class MsgWork(start: Int, size: Int) extends MsgBase
case class MsgResult(value: Double) extends MsgBase

case class PiApproximation(pi: Double, duration: Duration)

class WorkerActor extends Actor {

  val log = Logging(context.system, this)

  override def preStart() = {
    log.info("init : " + self.path)
  }

  override def postStop() = {
    log.info("done : " + self.path)
  }

  def calculatePiFor(start: Int, size: Int): Double = {

    var acc = 0.0

    for (i <- start until (start + size))
      acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)

    acc

  }

  def receive = {

    case MsgWork(start, size) =>
      //      log.info("start=" + start)
      sender ! MsgResult(calculatePiFor(start, size))

  }

}

class MasterActor(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, listener: ActorRef)
  extends Actor {

  var pi: Double = _

  var nrOfResults: Int = _

  val start: Long = System.currentTimeMillis

  val workerRouter = context.actorOf( //
    Props[WorkerActor].withRouter(RoundRobinRouter(nrOfWorkers)), //
    name = "router")

  def receive = {

    case MsgStart =>
      for (i <- 0 until nrOfMessages)
        workerRouter ! MsgWork(i * nrOfElements, nrOfElements)

    case MsgResult(value) =>

      pi += value

      nrOfResults += 1

      if (nrOfResults == nrOfMessages) {

        // Send the result to the listener
        listener ! PiApproximation(pi, duration = (System.currentTimeMillis - start).millis)

        // Stops this actor and all its supervised children
        context.stop(self)

      }

  }

}

class ListenerActor extends Actor {

  def receive = {

    case PiApproximation(pi, duration) =>

      println("\n\tPi approximation: \t\t%s\n\tCalculation time: \t%s"
        .format(pi, duration))

      context.system.shutdown()

  }

}

class Pi {

}

object Pi extends App {

  // actors and messages 
  def calculate(sizeWorkers: Int, sizeElements: Int, sizeMessages: Int) {

    // Create an Akka system
    val system = ActorSystem("pi-system")

    // create the result listener, which will print the result and shutdown the system
    val listener = system.actorOf( //
      Props[ListenerActor], //
      name = "listener")

    // create the master
    val master = system.actorOf( //
      props = Props( //
        new MasterActor(
          sizeWorkers, sizeMessages, sizeElements, listener)),
      name = "master")

    // start the calculation
    master ! MsgStart

  }

  calculate(sizeWorkers = 6, sizeElements = 10000, sizeMessages = 10000)

}
