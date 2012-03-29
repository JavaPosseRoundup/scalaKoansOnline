package support

import org.scalatest.Reporter
import org.scalatest.events.Event

class KoanReporter extends Reporter {

  override def apply(event: Event) {
    println(event)
  }

}
