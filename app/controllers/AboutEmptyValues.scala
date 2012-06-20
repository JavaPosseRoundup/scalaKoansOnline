package controllers

import play.api.data.Forms.text
import play.api.data.Forms.tuple
import play.api.data.Form
import play.api.mvc.Action
import play.Logger
import koanrunner.KoanRunner


object AboutEmptyValues extends Koan {

  type T = (String, String, String, String, String, String, String, String, String, 
        String, String, String, String, String, String, String)
  
  val name = "AboutEmptyValues"
  
  val form = Form(
      tuple(
          "1" -> text,
          "2" -> text,
          "3" -> text,
          "4" -> text,
          "5" -> text,
          "6" -> text,
          "7" -> text,
          "8" -> text,
          "9" -> text,
          "10" -> text,
          "11" -> text,
          "12" -> text,
          "13" -> text,
          "14" -> text,
          "15" -> text,
          "16" -> text
      ) 
  )
  
  def init = Action {
    Ok(views.html.aboutEmptyValues(name, form, Map.empty))
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
          Ok(views.html.aboutEmptyValues(name, f, results))
        }
      } getOrElse BadRequest
    }
  }

  
  
  val source = 
"""
import org.functionalkoans.forscala.support.KoanSuite
import org.functionalkoans.forscala.support.BlankValues._
import org.scalatest.matchers.ShouldMatchers
    
class AboutEmptyValues extends KoanSuite with ShouldMatchers {
    
  koan("None equals None") {
    assert(None === __)
  }

  koan("None should be identical to None") {
    val a = None
    assert(a eq __) // note that eq denotes identity, and == denotes equality in Scala
  }

  koan("None can be converted to a String") {
    assert(None.toString === __)
  }

  koan("An empty list can be represented by another nothing value: Nil") {
    assert(List() === __)
  }

  koan("None can be converted to an empty list") {
    val a = None
    assert(a.toList === __)
  }

  koan("None is considered empty") {
    assert(None.isEmpty === __)
  }

  koan("None can be cast Any, AnyRef or AnyVal") {
    assert(None.asInstanceOf[Any] === __)
    assert(None.asInstanceOf[AnyRef] === __)
    assert(None.asInstanceOf[AnyVal] === __)
  }

  koan("None cannot be cast to all types of objects") {
    intercept[ClassCastException] {
      // put the exception you expect to see in place of the blank
      assert(None.asInstanceOf[String] === __)
    }
  }

  koan("None can be used with Option instead of null references") {
    val optional: Option[String] = None
    assert(optional.isEmpty === __)
    assert(optional === __)
  }

  koan("Some is the opposite of None for Option types") {
    val optional: Option[String] = Some("Some Value")
    assert((optional == None) === __, "Some(value) should not equal None")
    assert(optional.isEmpty === __, "Some(value) should not be empty")
  }

  koan("Option.getOrElse can be used to provide a default in the case of None") {
    val optional: Option[String] = Some("Some Value")
    val optional2: Option[String] = None
    assert(optional.getOrElse("No Value") === __, "Should return the value in the option")
    assert(optional2.getOrElse("No Value") === __, "Should return the specified default value")
  }
}
    
new AboutEmptyValues
"""

}