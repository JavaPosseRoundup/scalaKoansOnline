package controllers

import play.api._
import play.api.mvc._

import com.twitter.util.Eval
import support.KoanSuite
import org.scalatest.{Filter, Stopper, Tracker}
import koanrunner.{TestResult, KoanReporter}

import scala.collection.mutable.Map

object Application extends Controller {
  
  def index = Action {

    val someScala = """
    import org.scalatest.matchers.ShouldMatchers
    import support.KoanSuite
    
    class AboutAsserts extends KoanSuite with ShouldMatchers {
        koan("asserts can take a boolean argument") {
            assert(true) // should be true
        }
        koan("foo") {
            assert(false)
        }
    }
    
    new AboutAsserts
    """

    val suite = Eval[KoanSuite](someScala)

    val results = Map[String, TestResult]()

    suite.run(None, new KoanReporter(results), new Stopper {}, Filter(), scala.collection.immutable.Map[String, Any](), None, new Tracker)

    Ok(views.html.index("Your new application is ready."))
  }
  
}