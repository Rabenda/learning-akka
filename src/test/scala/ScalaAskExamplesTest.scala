import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._
import org.scalatest.{FunSpecLike, Matchers}
import pong.ScalaPongActor

import scala.concurrent.Await

class ScalaAskExamplesTest extends FunSpecLike with Matchers{
  val system = ActorSystem()
  implicit val timeout = Timeout(5 seconds)

  val pongActor = system.actorOf(Props(classOf[ScalaPongActor]))
  describe("Pong actor") {
    it("should respond with Pong") {
      val future = pongActor ? "Ping"
      val result = Await.result(future.mapTo[String], 1 seconds)
      assert(result == "Pong")
    }
    it("should failed on unknown message") {
      val future = pongActor ? "unknown"
      intercept[Exception](
        Await.result(future.mapTo[String], 1 seconds)
      )
    }
  }
}
