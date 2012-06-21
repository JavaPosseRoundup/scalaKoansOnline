import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.{GetResponse, Channel, ConnectionFactory}
import messaging.MessageBus
import play.api.GlobalSettings
import play.api.Application
import play.Logger

object Global extends GlobalSettings {

  override def onStart(app: Application) {

    app.configuration.getString("rabbitmq.uri") map { uri =>
      MessageBus.setup(uri)
    } getOrElse {
      Logger.warn("No rabbitmq.uri specified")
    }

  }

}
