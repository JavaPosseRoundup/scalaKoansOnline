import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.{GetResponse, Channel, ConnectionFactory}
import messaging.MessageBus
import play.api.GlobalSettings

import play.api.Application

object Global extends GlobalSettings {

  override def onStart(app: Application) {

    app.configuration.getString("rabbitmq.uri") map { uri =>
      MessageBus.setup(uri)
    } getOrElse {
      sys.error("No rabbitmq.uri specified")
    }

  }

}
