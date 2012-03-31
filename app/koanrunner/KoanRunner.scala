package koanrunner

import messaging.MessageBus
import util.Properties
import com.rabbitmq.client.StringRpcServer
import support.KoanSuite
import com.twitter.util.Eval
import org.scalatest.{Filter, Tracker, Stopper}
import com.codahale.jerkson.Json

import scala.collection.mutable.Map

object KoanRunner extends App {

  Properties.envOrNone("RABBITMQ_URL") map  { uri =>

    MessageBus.setup(uri)

    val server: StringRpcServer = new StringRpcServer(MessageBus.channel, MessageBus.queueName) {
      override def handleStringCall(request: String) = {
        println("received: " + request)

        val suite = Eval[KoanSuite](request)

        val results = Map[String, TestResult]()

        suite.run(None, new KoanReporter(results), new Stopper {}, Filter(), scala.collection.immutable.Map[String, Any](), None, new Tracker)

        val json = Json.generate(results)
        println("returning: " + json)
        json
      }
    }

    server.mainloop()

  } getOrElse {
    sys.error("RABBITMQ_URL environment variable not found")
  }

}
