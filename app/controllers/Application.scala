package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
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
      scalaCodeForm.bindFromRequest.fold(
        errors => BadRequest,
        {
          case (inputScala) =>
            val outputScala = inputScala.reverse;
            Ok(html.output(outputScala))
        }
      )
  }
  
}