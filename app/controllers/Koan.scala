package controllers

import play.api.data.Form
import play.api.mvc.Action
import play.api.mvc.Controller
import koanrunner.KoanRunner
import play.Logger


abstract class Koan extends Controller {
  type T <: Product
  val name: String
  val form: Form[T]
  val source: String
  

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