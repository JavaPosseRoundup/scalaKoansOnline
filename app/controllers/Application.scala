package controllers

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
            val outputScala = KoanRunner.doEval(inputScala)
            Ok(html.output(outputScala))
        }
      } getOrElse BadRequest
  }
  
}