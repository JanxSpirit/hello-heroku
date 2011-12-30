import org.slf4j.LoggerFactory
import akka.actor.Supervisor
import akka.config.Supervision
import Supervision._
import cc.spray.connectors.Initializer
import akka.actor.Actor._
import cc.spray.HttpService._
import cc.spray.{HttpService, RootService}
import akka.event.slf4j.Logging

class SprayInitializer extends Initializer with Logging {

  val akkaConfig = akka.config.Config.config

  val msgService = actorOf(new HttpService(new MsgService{}.service))
  val rootService = actorOf(new RootService(msgService))

  // Start all actors that need supervision, including the root service actor.
  Supervisor(
    SupervisorConfig(
      OneForOneStrategy(List(classOf[Exception]), 3, 100),
      List(
        Supervise(msgService, Permanent),
        Supervise(rootService, Permanent)
      )
    )
  )
}

