package messaging

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.{RpcClient, ConnectionFactory, StringRpcServer, Channel}


object MessageBus {

  val exchangeName: String = "koan-exchange"
  val routingKey: String = "koan-routing-key"
  val exchangeType: String = "direct"
  val timeout = 20000

  var channel: Channel = _
  var queueName: String = _

  def setup(uri: String) {
    val factory = new ConnectionFactory()
    factory.setUri(uri)

    val conn = factory.newConnection()
    channel = conn.createChannel()
    channel.exchangeDeclare(exchangeName, exchangeType, true)
    queueName = channel.queueDeclare().getQueue
    channel.queueBind(queueName, exchangeName, routingKey)
  }

}
