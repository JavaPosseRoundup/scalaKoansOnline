package support

import org.scalatest._
import org.scalatest.matchers.{Matcher, MatchResult, ShouldMatchers}
import org.scalatest.events._

trait KoanSuite extends FunSuite with ShouldMatchers {

  def koan(name : String)(fun: => Unit) = test(name)(fun)

  def meditate() = pending

  def  __ : Matcher[Any] = {
    throw new TestPendingException
  }

  protected class ___ extends Exception {
    override def toString() = "___"
  }

}
