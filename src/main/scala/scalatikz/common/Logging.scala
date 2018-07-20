/*
 *
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * A PGF/TIKZ plot library for Scala.
 *
 */

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
