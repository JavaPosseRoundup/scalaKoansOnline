package controllers

import play.api.data.Form
import play.api.mvc.Action
import play.api.mvc.Controller
import koanrunner._
import play.Logger
import play.api.templates.Html

object Koan {
  
  private val koans: Map[String, Koan] = Map(
      AboutEmptyValues.name -> AboutEmptyValues
  );
  
  def apply(name: String) = koans(name)
}

abstract class Koan extends Controller {
  type T <: Product
  val name: String
  val form: Form[T]
  val source: String
  def template(name: String, form: Form[T], results: Map[String, TestResult]) : Html
  
  def init = Action {
    Ok(template(name, form, Map.empty))
  }  
  
  def eval = Action {
    implicit request => {
      val f = form.bindFromRequest
      f.value map {
        case (tuple) => {
          val params = for (param <- tuple.productIterator.toList)
            yield param match {
              case "" => "__"
              case s:String => s
              case _ => ""
            }
          val splitKoan = source.split("__").toList
          val inputScala = merge(splitKoan, params).reduceLeft(_ + _)
          val results = KoanRunner.doEval(inputScala)
          Ok(template(name, f, results))
        }
      } getOrElse BadRequest
    }
  }
  
  

  protected def merge[A](it1: List[A], it2: List[A]) : List[A] = {
      it1 match {
        case (h1 :: t1) => it2 match {
          case (h2 :: t2) => List(h1, h2) ::: merge(t1, t2)
          case Nil => List(h1) ::: merge(t1, Nil)
        }
        case Nil => it2 match {
          case (h2 :: t2) => List(h2) ::: merge(Nil, t2)
          case Nil => Nil
        }
      }
  }
  
  
  
}