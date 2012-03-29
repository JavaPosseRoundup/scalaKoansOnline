package koanrunner

import org.scalatest.events.{TestSucceeded, TestFailed, Event}
import org.scalatest.Reporter

import scala.collection.mutable.Map

class KoanReporter(resultMap: Map[String, TestResult]) extends Reporter {
  
  override def apply(event: Event) {
    event match {
      case t: TestSucceeded => resultMap += (t.testName -> TestResult(true, ""))
      case t: TestFailed => resultMap += (t.testName -> TestResult(false, t.message))
      case _ => 
    }
  }

}
