package views
import koanrunner.TestResult

object Util {

  def success(result: Option[TestResult]) = result match {
    case Some(r) => if (r.succeeded) "succeeded" else "failed"
    case None => "failed"
  }       
  
}
  
