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
