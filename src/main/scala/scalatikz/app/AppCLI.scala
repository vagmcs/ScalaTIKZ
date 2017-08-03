/*
 *    ____         __    ____________ ______
 *   / __/______ _/ /__ /_  __/  _/ //_/_  /
 *  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
 * /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
 *
 * ScalaTIKZ.
 *
 * Copyright (c) Evangelos Michelioudakis.
 *
 * This file is part of ScalaTIKZ.
 *
 * ScalaTIKZ is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * ScalaTIKZ is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ScalaTIKZ. If not, see <http://www.gnu.org/licenses/>.
 */

package scalatikz.app

import com.typesafe.scalalogging.LazyLogging
import scopt.OptionParser

/**
  * Command line basic abstraction.
  *
  * @param program program name
  * @tparam T type of configuration
  */
abstract class AppCLI[T](program: String)
  extends OptionParser[T](program) with LazyLogging with App {

  println {
    Console.CYAN +
    """
      |    ____         __    ____________ ______
      |   / __/______ _/ /__ /_  __/  _/ //_/_  /
      |  _\ \/ __/ _ `/ / _ `// / _/ // ,<   / /_
      | /___/\__/\_,_/_/\_,_//_/ /___/_/|_| /___/
    """.stripMargin + Console.BLINK
  }

  head("ScalaTIKZ:", "0.1")

  help("help").text("Print usage options.")

  version("version").text("Display the version.")

  override def reportError(msg: String): Unit = logger.error(msg)

  override def reportWarning(msg: String): Unit = logger.warn(msg)

  override def renderingMode = scopt.RenderingMode.TwoColumns

  override def showTryHelp(): Unit =
    if (helpOptions.nonEmpty)
      logger.info(s"Try ${helpOptions.head.fullName} for more information.")
}