package koanrunner

import org.scalatest.events.{TestSucceeded, TestFailed, Event}
import org.scalatest.Reporter

class KoanReporter extends Reporter {
  
  var results: Map[String, TestResult] = Map.empty
  
  override def apply(event: Event) {
    results = event match {
      case t: TestSucceeded => results + (t.testName -> TestResult(true, ""))
      case t: TestFailed => results + (t.testName -> TestResult(false, t.message))
      case _ => results
    }
  }
  
}
