package scalatikz.common

import com.typesafe.scalalogging.LazyLogging
import org.slf4j.MarkerFactory

trait Logging extends LazyLogging {

  private val FATAL_ERROR_MARKER = MarkerFactory.getMarker("FATAL")

  def fatal(message: => String): Nothing = {
    logger.error(FATAL_ERROR_MARKER, message)
    sys.exit(1)
  }

  def fatal(message: => String, cause: Throwable): Nothing = {
    logger.error(FATAL_ERROR_MARKER, message, cause)
    sys.exit(1)
  }
}
