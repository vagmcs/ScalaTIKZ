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

package scalatikz.app

import scopt.{ OptionParser, RenderingMode }
import scalatikz.BuildVersion
import scalatikz.common.{ Colors, Logging }

/**
 * Command line basic abstraction.
 *
 * @param program program name
 * @tparam T type of option configuration
 */
abstract class AppCLI[T <: OptionConf](program: String)
    extends OptionParser[T](program) with Colors with Logging with App {

  println {
    """
      |    ____         __    ____________ ______
      |   / __/______ _/ /__ /_  __/  _/ //_/_  /
      |  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
      | /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
    """.stripMargin.blue
  }

  head("ScalaTIKZ:".cyan, BuildVersion().red)

  override def reportError(msg: String): Unit = logger.error(msg)

  override def reportWarning(msg: String): Unit = logger.warn(msg)

  override def renderingMode: RenderingMode.OneColumn.type = scopt.RenderingMode.OneColumn

  override def showTryHelp(): Unit =
    if (helpOptions.nonEmpty) logger.info(s"Try ${helpOptions.head.fullName} for more information.")
}
