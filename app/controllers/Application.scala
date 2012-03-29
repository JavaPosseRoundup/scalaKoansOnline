package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views.html

import com.twitter.util.Eval
import support.KoanSuite
import org.scalatest.{Filter, Stopper, Tracker}
import koanrunner.{TestResult, KoanReporter}

import scala.collection.mutable.Map

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
            val outputScala = doEval(inputScala)
            Ok(html.output(outputScala))
        }
      } getOrElse BadRequest
  }

  def doEval(scalaCode: String) = {
    val suite = Eval[KoanSuite](scalaCode)

    val results = Map[String, TestResult]()

    suite.run(None, new KoanReporter(results), new Stopper {}, Filter(), scala.collection.immutable.Map[String, Any](), None, new Tracker)

    results
  }
  
}