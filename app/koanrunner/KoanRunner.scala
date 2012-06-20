package koanrunner

import play.Logger
import com.twitter.util.Eval
import org.functionalkoans.forscala.support.KoanSuite
import org.scalatest.Stopper
import org.scalatest.Filter
import org.scalatest.Tracker

object KoanRunner {

  def doEval(scalaCode: String) = {
    val eval = new Eval
    Logger.debug("Eval's classpath = " + eval.impliedClassPath);
    val suite = eval[KoanSuite](scalaCode)

    val reporter = new KoanReporter
    suite.run(None, reporter, new Stopper() {}, Filter(), Map.empty, None, new Tracker)

    reporter.results
  }
  

}