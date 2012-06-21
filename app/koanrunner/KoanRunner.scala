package koanrunner

import messaging.MessageBus
import util.Properties
import com.rabbitmq.client.StringRpcServer
import org.functionalkoans.forscala.support.KoanSuite
import com.twitter.util.Eval
import org.scalatest.{Filter, Tracker, Stopper}
import com.codahale.jerkson.Json
import play.Logger


object KoanRunner extends App {

  Properties.envOrNone("RABBITMQ_URL") map  { uri =>

    MessageBus.setup(uri)

    val server: StringRpcServer = new StringRpcServer(MessageBus.channel, MessageBus.queueName) {
      override def handleStringCall(request: String) = {
        println("received: " + request)

        val eval = new Eval
        val suite = eval[KoanSuite](request)

        val reporter = new KoanReporter
        suite.run(None, reporter, new Stopper {}, Filter(), Map.empty, None, new Tracker)

        val json = Json.generate(reporter.results)
        println("returning: " + json)
        json
      }
    }

    server.mainloop()

  } getOrElse {
    sys.error("RABBITMQ_URL environment variable not found")
  }

  def doEval(scalaCode: String) = {
    val eval = new Eval
    Logger.debug("Eval's classpath = " + eval.impliedClassPath);
    val suite = eval[KoanSuite](scalaCode)

    val reporter = new KoanReporter
    suite.run(None, reporter, new Stopper() {}, Filter(), Map.empty, None, new Tracker)

    reporter.results
  }

}
