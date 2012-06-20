package controllers

import scala.collection.mutable.Map

import messaging.MessageBus
import com.rabbitmq.client.RpcClient
import koanrunner.TestResult

import play.api.mvc.Controller
import play.api.mvc.Action
import play.api.data.Form
import play.api.data.Forms.{single, nonEmptyText}
import com.codahale.jerkson.Json


import org.functionalkoans.forscala.support.KoanSuite
import org.scalatest.Tracker

import koanrunner.KoanReporter
import koanrunner.KoanRunner
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import views.html

object Application extends Controller {
    
  val scalaCodeForm = Form(
    single(
      "inputScala" -> nonEmptyText
    )
  )
    
  def index = Action {
    Ok(views.html.index(scalaCodeForm))
  }
  
  def evaluateScalaCode = Action {
    implicit request =>
      scalaCodeForm.bindFromRequest.value map {
        case (inputScala) => {
          // send message to rabbitmq
          val rpcClient = new RpcClient(MessageBus.channel, MessageBus.exchangeName, MessageBus.routingKey, MessageBus.timeout)
          val response = rpcClient.stringCall(inputScala)

          val testResults = Json.parse[Map[String, TestResult]](response)

          Ok(views.html.output(testResults))

//            val outputScala = KoanRunner.doEval(inputScala)
//            Ok(html.output(outputScala))
        }
      } getOrElse BadRequest
  }
  
}
